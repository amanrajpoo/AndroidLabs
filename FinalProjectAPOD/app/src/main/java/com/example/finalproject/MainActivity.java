package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.finalproject.base.DrawerBaseActivity;

public class MainActivity extends DrawerBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_name));
        setActivityLayout(R.layout.content_main);

        Button btnNasa = findViewById(R.id.btnNasa);
        Button btnFav = findViewById(R.id.btnFavorites);
        Button btnSettings = findViewById(R.id.btnSettings);
        Button btnAbout = findViewById(R.id.btnAbout);

        btnNasa.setOnClickListener(v -> startActivity(new Intent(this, NasaActivity.class)));
        btnFav.setOnClickListener(v -> startActivity(new Intent(this, FavoritesActivity.class)));
        btnSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
        btnAbout.setOnClickListener(v -> startActivity(new Intent(this, AboutActivity.class)));
    }

    @Override
    protected String getHelpText() { return getString(R.string.help_main); }
}