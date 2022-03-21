package com.example.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper  {

    public static final String dbName="login.db";

    public DBHelper(Context context) {
        super(context, "login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
    myDB.execSQL("create Table users (username TEXT primary key, password TEXT, bmi1 TEXT , date1 TEXT, bmi2 TEXT , date2 TEXT , bmi3 TEXT , date3 TEXT , bmi4 TEXT , date4 TEXT , bmi5 TEXT , date5 TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
    myDB.execSQL("drop Table if exists users");
    }
    public boolean insertData(String username, String password){
         SQLiteDatabase myDB= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("username",username);
        cv.put("password",password);



        long result= myDB.insert("users",null,cv);
        if (result==-1) return false;
        else
            return true;
    }

    public boolean checkUsername(String username){
         SQLiteDatabase myDB= this.getWritableDatabase();
         Cursor cursor= myDB.rawQuery("Select * from users where username=?", new String[]{username});
         if(cursor.getCount()>0)
             return true;
         else
             return false;
    }

    public boolean checkusernamepassword(String username,String password){
        SQLiteDatabase myDB= this.getWritableDatabase();
        Cursor cursor= myDB.rawQuery("Select * from users where username=? and password=?", new String[]{username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public boolean deleteAccount(String username){
        SQLiteDatabase myDB= this.getWritableDatabase();
        myDB.delete("users","username = ?", new String[]{username});
            return true;

    }


    public boolean insertBMI(String bmi,String username){
        String currentDate;
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        SQLiteDatabase myDB= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        Cursor cursor1 = viewData(username);
        while (cursor1.moveToNext()) {

            if (cursor1.getString(2)==null && cursor1.getString(3)==null) {
                cv.put("bmi1", bmi);
                cv.put("date1", currentDate);
            }else  if (cursor1.getString(4)==null && cursor1.getString(5)==null) {
                cv.put("bmi2", bmi);
                cv.put("date2", currentDate);
            }else  if (cursor1.getString(6)==null && cursor1.getString(7)==null) {
                cv.put("bmi3", bmi);
                cv.put("date3", currentDate);
            }else  if (cursor1.getString(8)==null && cursor1.getString(9)==null) {
                cv.put("bmi4", bmi);
                cv.put("date4", currentDate);
            }else if (cursor1.getString(10)==null && cursor1.getString(11)==null){
                cv.put("bmi5", bmi);
                cv.put("date5", currentDate);
            }else{

            }
        }
        Cursor cursor = myDB.rawQuery("Select * from users where username=?",new String[]{username});
        if(cursor.getCount()>0) {
            long result= myDB.update("users",cv,"username=?",new String[]{username});
            if (result == -1) {
                return false;
            }else{
                return true;
            }
        }else{
            return  false;
        }
    }

    public Cursor viewData(String username){
        SQLiteDatabase myDB= this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where username=?",new String[]{username});
        return cursor;

    }

    public boolean clearData(String username){
        SQLiteDatabase myDB= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        Cursor cursor = myDB.rawQuery("Select * from users where username=?",new String[]{username});
        if(cursor.getCount()>0) {
            cv.put("bmi1", (byte[]) null);
            cv.put("date1", (byte[]) null);
            cv.put("bmi2", (byte[]) null);
            cv.put("date2", (byte[]) null);
            cv.put("bmi3", (byte[]) null);
            cv.put("date3", (byte[]) null);
            cv.put("bmi4", (byte[]) null);
            cv.put("date4", (byte[]) null);
            cv.put("bmi5", (byte[]) null);
            cv.put("date5", (byte[]) null);
            long result= myDB.update("users",cv,"username=?",new String[]{username});
            if (result == -1) {
                return false;
            }else{
                return true;
            }
        }else{
            return  false;
        }
    }

}
