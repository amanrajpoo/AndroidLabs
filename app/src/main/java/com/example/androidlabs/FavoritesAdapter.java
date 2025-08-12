package com.example.nasaimageoftheday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidlabs.R;

import java.util.List;

public class FavoritesAdapter extends BaseAdapter {
    private final Context context;
    private final List<FavoriteImage> favorites;

    public FavoritesAdapter(Context context, List<FavoriteImage> favorites) {
        this.context = context;
        this.favorites = favorites;
    }

    @Override
    public int getCount() {
        return favorites.size();
    }

    @Override
    public Object getItem(int position) {
        return favorites.get(position);
    }

    @Override
    public long getItemId(int position) {
        return favorites.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_favorite, parent, false);
        }

        TextView title = convertView.findViewById(R.id.favoriteTitle);
        TextView date = convertView.findViewById(R.id.favoriteDate);

        FavoriteImage favorite = favorites.get(position);
        title.setText(favorite.title);
        date.setText(favorite.date);

        return convertView;
    }
}
