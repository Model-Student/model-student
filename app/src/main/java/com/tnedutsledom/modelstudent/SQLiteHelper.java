package com.tnedutsledom.modelstudent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {


//    private static final String SQL_CREATE_ENTRIES =
//            "CREATE TABLE " + TableSecurity.FeedEntry.TABLE_NAME + " (" +
//                    TableSecurity.FeedEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
//                    TableSecurity.FeedEntry.COLUMN_DATE + " TEXT," +
//                    TableSecurity.FeedEntry.COLUMN_TEXT + " TEXT)";

    public static final String DATABASE_NAME = "LastTime.DB";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DATABASE_NAME;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
