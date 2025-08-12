package com.example.finalproject.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import com.example.finalproject.AboutActivity;
import com.example.finalproject.FavoritesActivity;
import com.example.finalproject.MainActivity;
import com.example.finalproject.NasaActivity;
import com.example.finalproject.R;
import com.example.finalproject.SettingsActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.drawerlayout.widget.DrawerLayout;


public abstract class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_drawer);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // after setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            try {
                String v = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                getSupportActionBar().setSubtitle(getString(R.string.version_fmt, v));
            } catch (Exception e) {
                getSupportActionBar().setSubtitle(getString(R.string.version_fmt, "1.0"));
            }
        }


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    protected void setActivityLayout(@LayoutRes int layoutRes) {
        FrameLayout container = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(layoutRes, container, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_help) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.menu_help)
                    .setMessage(getHelpText())
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
            return true;
        } else if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected String getHelpText() { return getString(R.string.help_default); }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) startActivity(new Intent(this, MainActivity.class));
        else if (id == R.id.nav_nasa) startActivity(new Intent(this, NasaActivity.class));
        else if (id == R.id.nav_favorites) startActivity(new Intent(this, FavoritesActivity.class));
        else if (id == R.id.nav_settings) startActivity(new Intent(this, SettingsActivity.class));
        else if (id == R.id.nav_about) startActivity(new Intent(this, AboutActivity.class));
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}