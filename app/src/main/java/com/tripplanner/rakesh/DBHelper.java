package com.tripplanner.rakesh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mytripinfo.db";
    public static final String TRIP_TABLE_NAME = "tripdata";
    public static final String TRIP_COLUMN_ID = "id";
    public static final String TRIP_COLUMN_TRIP_START_DATE = "tripStartDate";
    public static final String TRIP_COLUMN_VEHICLE_NUMBER = "vehicleNumber";
    public static final String TRIP_COLUMN_CT_NO = "ctNo";
    public static final String TRIP_COLUMN_FACTORY = "factory";
    public static final String TRIP_COLUMN_AGENT = "agent";
    public static final String TRIP_COLUMN_DRIVER_NAME = "driverName";
    public static final String TRIP_COLUMN_DRIVER_NUMBER = "driverNumber";
    public static final String TRIP_COLUMN_BILL_NUMBER = "billNumber";
    public static final String TRIP_COLUMN_BILL_DATE = "billDate";
    public static final String TRIP_COLUMN_DUE_DATE = "dueDate";
    public static final String TRIP_COLUMN_AMOUNT = "amount";
    public static final String TRIP_COLUMN_PAYMENT_STATUS = "paymentStatus";
    public static final String TRIP_COLUMN_RECEIVED_DATE = "receivedDate";
    public static final String TRIP_COLUMN_DISCOUNT = "discount";
    public static final String TRIP_COLUMN_DIESEL = "diesel";
    public static final String TRIP_COLUMN_ADVANCE = "advance";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table IF NOT EXISTS tripdata " +
                "(id integer primary key,tripstartdate text,vehiclenumber text,ctno text,factory text,agent text,drivername text,drivernumber text,billnumber text,billdate text,duedate text,amount text,paymentstatus text,receiveddate text, discount text,diesel text,advance text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tripdata");
        onCreate(sqLiteDatabase);

    }

    public boolean insertTripData(String tripStartDate, String vehicleNumber, String ctNo, String factory, String agent, String driverName, String driverNumber, String billNUmber, String billDate, String dueDate, String amount, String paymentStatus, String receivedDate, String discount, String diesel, String advance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tripStartDate", tripStartDate);
        contentValues.put("vehicleNumber", vehicleNumber);
        contentValues.put("ctNo", ctNo);
        contentValues.put("factory", factory);
        contentValues.put("agent", agent);

        contentValues.put("driverName", driverName);
        contentValues.put("driverNumber", driverNumber);
        contentValues.put("billNUmber", billNUmber);
        contentValues.put("billDate", billDate);
        contentValues.put("dueDate", dueDate);

        contentValues.put("amount", amount);
        contentValues.put("paymentStatus", paymentStatus);
        contentValues.put("receivedDate", receivedDate);
        contentValues.put("discount", discount);
        contentValues.put("diesel", diesel);
        contentValues.put("advance", advance);

        db.insert("tripdata", null, contentValues);
        return true;
    }


    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from tripdata where id=" + id + "", null);
        return res;
    }


    public List<String> getAllDrivers() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> driversList = new ArrayList<String>();
        Cursor cursor = db.rawQuery("select * from tripdata ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String driverNm = cursor.getString(6);
            driversList.add(driverNm);
            cursor.moveToNext();
        }
        return driversList;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "tripdata");
        return numRows;
    }

    public boolean updateContact(Integer id, String tripStartDate, String vehicleNumber, String ctNo, String factory, String agent, String driverName, String driverNumber, String billNUmber, String billDate, String dueDate, String amount, String paymentStatus, String receivedDate, String discount, String diesel, String advance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tripStartDate", tripStartDate);
        contentValues.put("vehicleNumber", vehicleNumber);
        contentValues.put("ctNo", ctNo);
        contentValues.put("factory", factory);
        contentValues.put("agent", agent);

        contentValues.put("driverName", driverName);
        contentValues.put("driverNumber", driverNumber);
        contentValues.put("billNUmber", billNUmber);
        contentValues.put("billDate", billDate);
        contentValues.put("dueDate", dueDate);

        contentValues.put("amount", amount);
        contentValues.put("paymentStatus", paymentStatus);
        contentValues.put("receivedDate", receivedDate);
        contentValues.put("discount", discount);
        contentValues.put("diesel", diesel);
        contentValues.put("advance", advance);
        db.update("tripdata", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteContact(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("tripdata",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }


    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from tripdata", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(TRIP_COLUMN_DRIVER_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}
