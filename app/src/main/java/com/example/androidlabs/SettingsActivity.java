package com.example.nasaimageoftheday;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchDarkMode;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs = getSharedPreferences("NASA_PREFS", MODE_PRIVATE);
        switchDarkMode = findViewById(R.id.switchDarkMode);

        boolean isDark = prefs.getBoolean("darkMode", false);
        switchDarkMode.setChecked(isDark);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) ->
                prefs.edit().putBoolean("darkMode", isChecked).apply());
    }
}
