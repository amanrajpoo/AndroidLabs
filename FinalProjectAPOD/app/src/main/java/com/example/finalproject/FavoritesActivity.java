package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.finalproject.base.DrawerBaseActivity;
import com.example.finalproject.data.FavoritesRepository;
import com.example.finalproject.models.FavoriteItem;
import com.example.finalproject.ui.FavoritesAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class FavoritesActivity extends DrawerBaseActivity {

    private FavoritesRepository repo;
    private FavoritesAdapter adapter;
    private List<FavoriteItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.title_favorites));
        setActivityLayout(R.layout.activity_favorites);

        repo = new FavoritesRepository(this);
        items = repo.getAll();

        ListView list = findViewById(R.id.listFavorites);
        adapter = new FavoritesAdapter(this, items);
        list.setAdapter(adapter);

        list.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            FavoriteItem item = items.get(position);
            Intent i = new Intent(this, FavoriteDetailActivity.class);
            i.putExtra("id", item.id);
            startActivity(i);
        });

        list.setOnItemLongClickListener((parent, view, position, id) -> {
            FavoriteItem item = items.get(position);
            repo.delete(item.id);
            items.remove(position);
            adapter.notifyDataSetChanged();
            Snackbar.make(findViewById(android.R.id.content), R.string.deleted, Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo, v -> {
                        long newId = repo.insert(item);
                        item.id = (int) newId;
                        items.add(position, item);
                        adapter.notifyDataSetChanged();
                    }).show();
            return true;
        });
    }

    @Override
    protected String getHelpText() { return getString(R.string.help_favorites); }
}