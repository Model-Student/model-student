package com.tnedutsledom.modelstudent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TableInfo.TABLE_NAME + " (" +
                    TableInfo.COLUMN_NAME_ID +" INTEGER PRIMARY KEY," +
                    TableInfo.COLUMN_DATE_TEXT + " TEXT," +
                    TableInfo.COLUMN_MAIN_TEXT + " TEXT)";

    public static final String DATABASE_NAME = "LastTime.DB";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DATABASE_NAME;


    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
