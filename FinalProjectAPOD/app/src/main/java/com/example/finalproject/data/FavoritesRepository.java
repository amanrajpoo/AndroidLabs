package com.example.finalproject.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalproject.models.FavoriteItem;

import java.util.ArrayList;
import java.util.List;

public class FavoritesRepository {
    private final AppDatabaseHelper helper;

    public FavoritesRepository(Context ctx) { helper = new AppDatabaseHelper(ctx); }

    public long insert(FavoriteItem item) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("date", item.date);
        cv.put("title", item.title);
        cv.put("url", item.url);
        cv.put("hdurl", item.hdurl);
        cv.put("local_path", item.localPath);
        cv.put("saved_at", item.savedAt);
        return db.insert("favorites", null, cv);
    }

    public List<FavoriteItem> getAll() {
        List<FavoriteItem> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id,date,title,url,hdurl,local_path,saved_at FROM favorites ORDER BY saved_at DESC", null);
        while (c.moveToNext()) {
            list.add(new FavoriteItem(
                    c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getLong(6)));
        }
        c.close();
        return list;
    }

    public FavoriteItem getById(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id,date,title,url,hdurl,local_path,saved_at FROM favorites WHERE id=?", new String[]{String.valueOf(id)});
        FavoriteItem item = null;
        if (c.moveToFirst()) {
            item = new FavoriteItem(
                    c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getLong(6));
        }
        c.close();
        return item;
    }

    public void delete(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("favorites", "id=?", new String[]{String.valueOf(id)});
    }
}