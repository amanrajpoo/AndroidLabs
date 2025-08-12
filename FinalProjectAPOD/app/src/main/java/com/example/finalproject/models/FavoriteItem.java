package com.example.finalproject.models;

public class FavoriteItem {
    public int id;
    public String date;
    public String title;
    public String url;
    public String hdurl;
    public String localPath; // internal storage path
    public long savedAt;

    public FavoriteItem(int id, String date, String title, String url, String hdurl, String localPath, long savedAt) {
        this.id = id; this.date = date; this.title = title; this.url = url; this.hdurl = hdurl; this.localPath = localPath; this.savedAt = savedAt;
    }
}