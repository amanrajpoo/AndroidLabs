package com.example.finalproject;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.finalproject.base.DrawerBaseActivity;
import com.example.finalproject.data.FavoritesRepository;
import com.example.finalproject.models.FavoriteItem;
import com.example.finalproject.ui.DetailFragment;

public class FavoriteDetailActivity extends DrawerBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.title_detail));
        setActivityLayout(R.layout.activity_favorite_detail);

        int id = getIntent().getIntExtra("id", -1);
        FavoritesRepository repo = new FavoritesRepository(this);
        FavoriteItem item = repo.getById(id);
        if (item != null) {
            DetailFragment fragment = DetailFragment.newInstance(
                    item.title, item.date, item.url, item.hdurl, null, item.localPath);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, fragment)
                    .commit();
        }
    }

    @Override
    protected String getHelpText() { return getString(R.string.help_detail); }
}