package com.tripplanner.rakesh;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.media.tv.TvContract;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.util.Date;

import static android.os.Build.ID;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 200;

    FileOutputStream outputStream;
    SQLiteDatabase mydb;
    DBHelper dbHelper;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;

    private Toolbar toolBar;

    Button addinfobtn;

    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;
    int id = 1;

    String channelId="File Download Channel";


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private int cellnum;

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;

                case R.id.navigation_dashboard:
                    Toast.makeText(getApplicationContext(), "Clicked on dashboard", Toast.LENGTH_LONG).show();
                    Intent dashboardIntent = new Intent(MainActivity.this, DisplayData.class);
                    startActivity(dashboardIntent);
                    return true;

                case R.id.navigation_notifications:

                    return true;
                case R.id.navigation_excel:
                    mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mBuilder = new NotificationCompat.Builder(MainActivity.this,channelId);
                    mBuilder.setContentTitle("File Download")
                            .setContentText("Download in progress")
                            .setSmallIcon(R.drawable.icons8_downloading_updates_16)
                    .setChannelId(channelId);
                    Log.d("TAG", "Notification !!!!");
                    new Thread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    int incr;
                                    // Do the "lengthy" operation 20 times
                                    for (incr = 0; incr <= 100; incr+=5) {
                                        // Sets the progress indicator to a max value, the current completion percentage and "determinate" state
                                        mBuilder.setProgress(100, incr, false);
                                        // Displays the progress bar for the first time.
                                        mNotifyManager.notify(id, mBuilder.build());
                                        // Sleeps the thread, simulating an operation
                                        try {
                                            // Sleep for 1 second
                                            Log.d("TAG", "Thread Sleep");
                                            Thread.sleep(1*1000);
                                        } catch (InterruptedException e) {
                                            Log.d("TAG", "sleep failure");
                                        }
                                    }
                                    // When the loop is finished, updates the notification
                                    mBuilder.setContentText("Download completed")
                                            // Removes the progress bar
                                            .setProgress(0,0,false);
                                    mNotifyManager.notify(id, mBuilder.build());
                                }
                            }
                            // Starts the thread by calling the run() method in its Runnable
                    ).start();

                    String respnse = writeExcel();

                    return true;
            }
            return false;
        }
    };


    //Write data to excel and download
    private String writeExcel() {
        dbHelper = new DBHelper(this);
        mydb = dbHelper.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("select * from tripdata ", null);
        cursor.moveToFirst();
        String[] columnNames = cursor.getColumnNames();
        Workbook workbook = new SXSSFWorkbook(100);
        Sheet sheet = workbook.createSheet("Trip Details!");
        Font headerFont = workbook.createFont();
        headerFont.setFontName("Arial");
        headerFont.setItalic(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.LIGHT_ORANGE.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        Row headerRow = sheet.createRow(0);


        for (int cellnum = 0; cellnum < columnNames.length; cellnum++) {
            Cell cell = headerRow.createCell(cellnum);
            cell.setCellValue(columnNames[cellnum]);
            cell.setCellStyle(headerCellStyle);
            sheet.setColumnWidth(cellnum, (15 * 300));


        }


        Font dataFont = workbook.createFont();
        dataFont.setFontName("Arial");

        dataFont.setFontHeightInPoints((short) 14);

        CellStyle dataCellStyle = workbook.createCellStyle();

        int rowCreat = 0;
        while (!cursor.isAfterLast()) {
            rowCreat++;
            Row dataRow = sheet.createRow(rowCreat);
            for (int rowCount = 0; rowCount < columnNames.length; rowCount++) {
                Cell dataCell = dataRow.createCell(rowCount);
                if (cursor.getString(rowCount).isEmpty() || cursor.getString(rowCount) == null) {
                    dataCell.setCellValue("--");
                }
                dataCell.setCellValue(cursor.getString(rowCount));


                sheet.setColumnWidth(rowCount, (15 * 300));
                if ("Due".equalsIgnoreCase(cursor.getString(12))) {
                    dataFont.setColor(IndexedColors.RED.getIndex());
                }
                dataCellStyle.setFont(dataFont);


            }
            cursor.moveToNext();
            sendNotification();

        }


        Log.i("Log Test", "After Writing excel");
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            Log.i("Dir Not Found", "Inside Dir Not Exist" + dir.exists());
            if (!dir.exists()) {
                Log.i("Dir Not Found", "Inside Dir Not Exist");
                dir.mkdirs();
                dir.createNewFile();


            }
            FileOutputStream fOut = new FileOutputStream(new File(dir + "/" + new Date().getTime() + ".xlsx"));
            //  outputStream = openFileOutput(, Context.MODE_PRIVATE);
            workbook.write(fOut);
            // outputStream.write(fileContents.getBytes());
            fOut.close();
            Toast.makeText(getApplicationContext(), "File Saved in " + dir + "/" + new Date().getTime() + ".xlsx", Toast.LENGTH_LONG).show();


        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "Success";
    }

    private void sendNotification() {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        Activity activity = (Activity) getApplicationContext();
        verifyStoragePermissions(this);
        setContentView(R.layout.activity_main);
        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        addinfobtn = findViewById(R.id.addinfobtn);
        addinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

}
