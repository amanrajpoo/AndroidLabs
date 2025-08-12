package com.example.finalproject.network;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchApodTask extends AsyncTask<String, Void, JSONObject> {

    public interface Listener { void onApodResult(JSONObject json, String error); }

    private final Listener listener;
    private String error;

    public FetchApodTask(Listener listener) { this.listener = listener; }

    @Override
    protected JSONObject doInBackground(String... strings) {
        String urlStr = strings[0];
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestMethod("GET");
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            br.close();
            return new JSONObject(sb.toString());
        } catch (Exception e) {
            error = e.getMessage();
            return null;
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (listener != null) listener.onApodResult(jsonObject, error);
    }
}