package com.example.finalproject.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.models.FavoriteItem;

import java.util.List;

public class FavoritesAdapter extends BaseAdapter {
    private final Context ctx; private final List<FavoriteItem> data;
    public FavoritesAdapter(Context ctx, List<FavoriteItem> data) { this.ctx = ctx; this.data = data; }

    @Override public int getCount() { return data.size(); }
    @Override public Object getItem(int position) { return data.get(position); }
    @Override public long getItemId(int position) { return data.get(position).id; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) v = LayoutInflater.from(ctx).inflate(R.layout.item_favorite, parent, false);
        FavoriteItem it = data.get(position);
        ((TextView) v.findViewById(R.id.rowTitle)).setText(it.title);
        ((TextView) v.findViewById(R.id.rowSub)).setText(ctx.getString(R.string.date_fmt, it.date));
        return v;
    }
}