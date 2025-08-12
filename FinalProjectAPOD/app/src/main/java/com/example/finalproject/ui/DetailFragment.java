package com.example.finalproject.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalproject.R;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailFragment extends Fragment {

    private String title, date, url, hdurl, explanation, localPath;

    public static DetailFragment newInstance(String title, String date, String url, String hdurl, String explanation, String localPath) {
        DetailFragment f = new DetailFragment();
        Bundle b = new Bundle();
        b.putString("title", title);
        b.putString("date", date);
        b.putString("url", url);
        b.putString("hdurl", hdurl);
        b.putString("explanation", explanation);
        b.putString("localPath", localPath);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        TextView tTitle = v.findViewById(R.id.detailTitle);
        TextView tDate = v.findViewById(R.id.detailDate);
        TextView tExpl = v.findViewById(R.id.detailExplanation);
        ImageView img = v.findViewById(R.id.detailImage);
        Button btnOpen = v.findViewById(R.id.btnOpenInBrowser);

        Bundle b = getArguments();
        if (b != null) {
            title = b.getString("title");
            date = b.getString("date");
            url = b.getString("url");
            hdurl = b.getString("hdurl");
            explanation = b.getString("explanation");
            localPath = b.getString("localPath");
        }

        tTitle.setText(title);
        tDate.setText(getString(R.string.date_fmt, date));
        tExpl.setText(explanation == null ? "" : explanation);

        if (localPath != null) {
            File f = new File(localPath);
            if (f.exists()) img.setImageBitmap(BitmapFactory.decodeFile(localPath));
        } else if (url != null && url.startsWith("http")) {
            new AsyncTask<Void, Void, Bitmap>() {
                @Override protected Bitmap doInBackground(Void... voids) {
                    try {
                        HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
                        c.connect();
                        InputStream is = c.getInputStream();
                        return BitmapFactory.decodeStream(is);
                    } catch (Exception ignored) {}
                    return null;
                }
                @Override protected void onPostExecute(Bitmap bmp) { if (bmp != null) img.setImageBitmap(bmp); }
            }.execute();
        }

        btnOpen.setOnClickListener(v1 -> {
            if (hdurl != null) startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(hdurl)));
        });
        return v;
    }
}