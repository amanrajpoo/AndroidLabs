package com.example.finalproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.finalproject.base.DrawerBaseActivity;
import com.example.finalproject.data.FavoritesRepository;
import com.example.finalproject.models.FavoriteItem;
import com.example.finalproject.network.DownloadImageTask;
import com.example.finalproject.network.FetchApodTask;
import com.example.finalproject.ui.DetailFragment;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NasaActivity extends DrawerBaseActivity implements FetchApodTask.Listener, DownloadImageTask.Listener {

    private EditText dateInput;
    private Button pickBtn, fetchBtn, openBtn, saveBtn;
    private ProgressBar progress;
    private TextView titleTxt, dateTxt;
    private ImageView imageView;

    private String currentTitle, currentDate, currentUrl, currentHdUrl, currentExplanation;

    private FavoritesRepository repo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.title_nasa));
        setActivityLayout(R.layout.activity_nasa);

        repo = new FavoritesRepository(this);

        dateInput = findViewById(R.id.editDate);
        pickBtn = findViewById(R.id.btnPickDate);
        fetchBtn = findViewById(R.id.btnFetch);
        openBtn = findViewById(R.id.btnOpenHd);
        saveBtn = findViewById(R.id.btnSave);
        progress = findViewById(R.id.progress);
        titleTxt = findViewById(R.id.txtTitle);
        dateTxt = findViewById(R.id.txtDate);
        imageView = findViewById(R.id.imageView);

        dateInput.setInputType(InputType.TYPE_CLASS_DATETIME);

        // Load last date from SharedPreferences
        String lastDate = getSharedPreferences("prefs", MODE_PRIVATE).getString("lastDate", "2020-02-01");
        dateInput.setText(lastDate);

        pickBtn.setOnClickListener(v -> showDatePicker());
        fetchBtn.setOnClickListener(v -> fetch());
        openBtn.setOnClickListener(v -> {
            if (currentHdUrl != null) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(currentHdUrl));
                startActivity(i);
            } else {
                Toast.makeText(this, R.string.no_hdurl, Toast.LENGTH_SHORT).show();
            }
        });
        saveBtn.setOnClickListener(v -> save());
    }

    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, (DatePicker view, int year, int month, int dayOfMonth) -> {
            Calendar c = Calendar.getInstance();
            c.set(year, month, dayOfMonth);
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(c.getTime());
            dateInput.setText(date);
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void fetch() {
        String date = dateInput.getText().toString().trim();
        if (date.isEmpty()) {
            Toast.makeText(this, R.string.enter_date, Toast.LENGTH_SHORT).show();
            return;
        }
        getSharedPreferences("prefs", MODE_PRIVATE).edit().putString("lastDate", date).apply();

        String apiKey = getSharedPreferences("prefs", MODE_PRIVATE).getString("apiKey", getString(R.string.demo_api_key));
        String url = "https://api.nasa.gov/planetary/apod?api_key=" + apiKey + "&date=" + date;
        progress.setVisibility(View.VISIBLE);
        new FetchApodTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
    }

    @Override
    public void onApodResult(JSONObject json, String error) {
        progress.setVisibility(View.GONE);
        if (error != null) {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_fmt, error), Snackbar.LENGTH_LONG).show();
            return;
        }
        if (json == null) return;
        currentTitle = json.optString("title");
        currentDate = json.optString("date");
        currentUrl = json.optString("url");
        currentHdUrl = json.optString("hdurl", json.optString("url"));
        currentExplanation = json.optString("explanation");

        titleTxt.setText(currentTitle);
        dateTxt.setText(getString(R.string.date_fmt, currentDate));

        // Show details in Fragment
        DetailFragment fragment = DetailFragment.newInstance(currentTitle, currentDate, currentUrl, currentHdUrl, currentExplanation, null);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detail_container, fragment)
                .commit();

        Toast.makeText(this, R.string.fetched, Toast.LENGTH_SHORT).show();
    }

    private void save() {
        if (currentDate == null) {
            Toast.makeText(this, R.string.nothing_to_save, Toast.LENGTH_SHORT).show();
            return;
        }
        // Download HD image to internal storage then save record
        progress.setVisibility(View.VISIBLE);
        new DownloadImageTask(this, getFilesDir()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, currentHdUrl);
    }

    @Override
    public void onImageSaved(String localPath, String error) {
        progress.setVisibility(View.GONE);
        if (error != null) {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_fmt, error), Snackbar.LENGTH_LONG).show();
            return;
        }
        FavoriteItem item = new FavoriteItem(0, currentDate, currentTitle, currentUrl, currentHdUrl, localPath, System.currentTimeMillis());
        long id = repo.insert(item);
        if (id > 0) {
            Snackbar.make(findViewById(android.R.id.content), R.string.saved, Snackbar.LENGTH_LONG)
                    .setAction(R.string.view_favorites, v -> startActivity(new Intent(this, FavoritesActivity.class)))
                    .show();
        }
    }

    @Override
    protected String getHelpText() { return getString(R.string.help_nasa); }
}