package com.example.finalproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.base.DrawerBaseActivity;

public class SettingsActivity extends DrawerBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.title_settings));
        setActivityLayout(R.layout.activity_settings);

        EditText apiKey = findViewById(R.id.editApiKey);
        EditText defaultDate = findViewById(R.id.editDefaultDate);
        Button save = findViewById(R.id.btnSaveSettings);

        apiKey.setText(getSharedPreferences("prefs", MODE_PRIVATE).getString("apiKey", getString(R.string.demo_api_key)));
        defaultDate.setText(getSharedPreferences("prefs", MODE_PRIVATE).getString("lastDate", "2020-02-01"));

        save.setOnClickListener(v -> {
            getSharedPreferences("prefs", MODE_PRIVATE).edit()
                    .putString("apiKey", apiKey.getText().toString().trim())
                    .putString("lastDate", defaultDate.getText().toString().trim())
                    .apply();
            Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected String getHelpText() { return getString(R.string.help_settings); }
}