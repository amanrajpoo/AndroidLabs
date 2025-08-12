package com.example.nasaimageoftheday;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlabs.R;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    private ListView listView;
    private FavoritesAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        listView = findViewById(R.id.listViewFavorites);
        db = AppDatabase.getDatabase(this);

        List<FavoriteImage> favorites = db.favoriteDao().getAllFavorites(); // <- update if DAO name differs
        adapter = new FavoritesAdapter(this, favorites);
        listView.setAdapter(adapter);
    }
}
