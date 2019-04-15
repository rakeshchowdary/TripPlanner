package com.tripplanner.rakesh;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Date;

public class DisplayData extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase mydb;
    private Toolbar toolBar;
    Button addBtn;
    AlertDialog.Builder builder;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(DisplayData.this, MainActivity.class);
                    startActivity(intent);
                    return true;

                case R.id.navigation_dashboard:

                    return true;

                case R.id.navigation_notifications:
                    return true;
                case R.id.navigation_excel:
                    writeExcel();


                    return true;
            }
            return false;
        }
    };


    //Write data to excel and download
    private void writeExcel() {
        dbHelper = new DBHelper(this);
        mydb = dbHelper.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("select * from tripdata ", null);
        cursor.moveToFirst();
        String[] columnNames = cursor.getColumnNames();
        Workbook workbook = new SXSSFWorkbook(100);
        Sheet sheet = workbook.createSheet("Trip Details");
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
            Toast.makeText(getApplicationContext(), "File Saved in " + dir, Toast.LENGTH_LONG).show();


        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_data);
        dbHelper = new DBHelper(this);
        mydb = dbHelper.getWritableDatabase();
      //  addBtn = findViewById(R.id.addDatabtn);



        /*//toolbar
        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);*/

        ArrayList<String> driversList = new ArrayList<String>();
        Cursor cursor = mydb.rawQuery("select * from tripdata ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String driverNm = cursor.getString(6);
            driversList.add(driverNm);
            cursor.moveToNext();
        }

        LinearLayout linearLayout = findViewById(R.id.rootLayout);

        for (int i = 0; i < driversList.size(); i++) {
            final Button btn = new Button(this);
            linearLayout.addView(btn);
            btn.setText(driversList.get(i));
            btn.setBackgroundColor(Color.parseColor("#FFAAE98A"));
            btn.setFocusableInTouchMode(true);
            btn.setWidth(320);
            btn.setEnabled(true);
            btn.setClickable(true);
            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(8);

            if (i % 2 == 0) {
                shape.setColor(Color.parseColor("#E8EDE8CD"));
            } else {
                shape.setColor(Color.parseColor("#E8EDE8CD"));
            }
            btn.setBackground(shape);
            btn.setHeight(40);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Cursor cursor = mydb.rawQuery("select * from tripdata where driverName="+"'"+btn.getText()+"'", null);
                    Toast.makeText(getApplicationContext(), "Clicked On Driver "+btn.getText(), Toast.LENGTH_LONG).show();
                    cursor.moveToFirst();
                    Intent intent = new Intent(DisplayData.this,EditInfo.class);
                    while (!cursor.isAfterLast()) {
                        String driverNm = cursor.getString(6);
                       Log.d("Driver Name","Clicked on "+driverNm);
                        intent.putExtra("SerialNumber",cursor.getString(0));
                        intent.putExtra("TripStartDate",cursor.getString(1));
                        intent.putExtra("VehicleNumber",cursor.getString(2));
                        intent.putExtra("CtNo",cursor.getString(3));
                        intent.putExtra("Factory",cursor.getString(4));
                        intent.putExtra("Agent",cursor.getString(5));
                        intent.putExtra("DriverName",cursor.getString(6));
                        intent.putExtra("DriverNumber",cursor.getString(7));
                        intent.putExtra("BillNumber",cursor.getString(8));
                        intent.putExtra("BillDate",cursor.getString(9));
                        intent.putExtra("DueDate",cursor.getString(10));
                        intent.putExtra("Amount",cursor.getString(11));
                        intent.putExtra("PaymentStatus",cursor.getString(12));
                        intent.putExtra("ReceivedDate",cursor.getString(13));
                        intent.putExtra("Discount",cursor.getString(14));
                        intent.putExtra("Diesel",cursor.getString(15));
                        intent.putExtra("Advance",cursor.getString(16));

                        cursor.moveToNext();
                    }

                    startActivity(intent);

                }
            });

        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuAbout:
                Toast.makeText(this, "You clicked about", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuClearAll:
                Toast.makeText(this, "You clicked menuClearAll", Toast.LENGTH_SHORT).show();
                //alert dialogue
                builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title).setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Toast.makeText(getApplicationContext(), "you choose yes action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                                dbHelper = new DBHelper(getApplicationContext());
                                mydb = dbHelper.getWritableDatabase();
                                mydb.execSQL("delete from  tripdata");

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "you choose no action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                break;


        }
        return true;
    }





}


