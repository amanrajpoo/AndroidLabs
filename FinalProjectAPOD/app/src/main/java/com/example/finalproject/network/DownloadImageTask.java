package com.example.finalproject.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, String> {

    public interface Listener { void onImageSaved(String localPath, String error); }

    private final Listener listener;
    private final File filesDir;
    private String error;

    public DownloadImageTask(Listener listener, File filesDir) {
        this.listener = listener; this.filesDir = filesDir;
    }

    @Override
    protected String doInBackground(String... strings) {
        String urlStr = strings[0];
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.connect();
            InputStream is = conn.getInputStream();
            Bitmap bmp = BitmapFactory.decodeStream(is);
            if (bmp == null) { error = "Bitmap decode failed"; return null; }
            File outFile = new File(filesDir, "apod_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream fos = new FileOutputStream(outFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush(); fos.close();
            return outFile.getAbsolutePath();
        } catch (Exception e) {
            error = e.getMessage();
            return null;
        } finally { if (conn != null) conn.disconnect(); }
    }

    @Override
    protected void onPostExecute(String path) {
        if (listener != null) listener.onImageSaved(path, error);
    }
}