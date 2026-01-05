package com.dinosoftlabs.qrandbarcodescanner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by AQEEL on 4/8/2017.
 */
public class Databaseclass extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Qrcode.db";
    Context contex;
    SQLiteDatabase sq = null;
    public Databaseclass(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        contex = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS datatable");
        onCreate(db);
    }


    // create two table one  to save scan history and other to save the code
    public void createTables(SQLiteDatabase db) {
        String historytable = "create table ScanHistory ( " +
                "ID integer  primary key autoincrement,Type TEXT, result TEXT," +
                " image BLOB, date TEXT)";

        String createtable = "create table CreateQR ( " +
                "ID integer  primary key autoincrement, title TEXT, Type TEXT, result TEXT," +
                " image BLOB, date TEXT)";
        try {
            db.execSQL(createtable);
            db.execSQL(historytable);
              } catch (Exception ex) {

        }
    }


    // add data of save history
    public long adddata(String type, String data, byte [] image, String date) {
        try {
            sq = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            sq.beginTransaction();
            cv.put("Type", type);
            cv.put("result", data);
            cv.put("image", image);
            cv.put("date",date);
            long result=sq.insert("ScanHistory", null, cv);
            sq.setTransactionSuccessful();
            sq.endTransaction();
            return result;
        } catch (Exception ex) {
            Toast.makeText(contex, "Error in adding time", Toast.LENGTH_SHORT).show();
            return -1;
    }
    }


    // add data of Qr code generated
    public long adddata_increate(String title,String type, String data, byte [] image, String date) {
        try {
            sq = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            sq.beginTransaction();
            cv.put("title",title);
            cv.put("Type", type);
            cv.put("result", data);
            cv.put("image", image);
            cv.put("date",date);
            long result=sq.insert("CreateQR", null, cv);
            sq.setTransactionSuccessful();
            sq.endTransaction();
            return result;
        } catch (Exception ex) {
            Toast.makeText(contex, "Error in adding time", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }



    // get the all data of scanhistory
    public Cursor get_all_data(){
        sq=this.getReadableDatabase();
        Cursor crclass=sq.rawQuery("select * from  ScanHistory",null);
        return crclass;
    }

    // get all the data of generated
    public Cursor get_all_create_data(){
        sq=this.getReadableDatabase();
        Cursor crclass=sq.rawQuery("select * from  CreateQR",null);
        return crclass;
    }


}