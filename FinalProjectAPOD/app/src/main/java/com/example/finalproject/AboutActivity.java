package com.example.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.example.finalproject.base.DrawerBaseActivity;

public class AboutActivity extends DrawerBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.title_about));
        setActivityLayout(R.layout.activity_about);

        TextView link = findViewById(R.id.txtLink);
        link.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.nasa.gov/"));
            startActivity(i);
        });
    }

    @Override
    protected String getHelpText() { return getString(R.string.help_default); }
}