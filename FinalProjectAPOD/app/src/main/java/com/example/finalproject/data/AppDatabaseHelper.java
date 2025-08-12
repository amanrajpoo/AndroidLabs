package com.example.finalproject.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "apod.db";
    public static final int DB_VERSION = 1;

    public AppDatabaseHelper(Context ctx) { super(ctx, DB_NAME, null, DB_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE favorites (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT, title TEXT, url TEXT, hdurl TEXT, local_path TEXT, saved_at INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS favorites");
        onCreate(db);
    }
}