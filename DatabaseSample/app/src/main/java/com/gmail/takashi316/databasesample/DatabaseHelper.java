package com.gmail.takashi316.databasesample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cocktaiilmemo.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }//constructor

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE cocktailmemo (");
        sb.append("_id INTEGER PRIMARY KEY, ");
        sb.append("name TEXT, ");
        sb.append("note TEXT");
        sb.append(")");
        String sql = sb.toString();
        sqLiteDatabase.execSQL(sql);
    }//onCreate

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }//onUpgrade
}//DatabaseHelper
