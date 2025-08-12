package com.example.nasaimageoftheday;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteImage {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String url;
    public String date;
    public String description;
}
