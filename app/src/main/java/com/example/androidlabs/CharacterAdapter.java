package com.example.androidlabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CharacterAdapter extends ArrayAdapter<StarWarsCharacter> {
    public CharacterAdapter(Context ctx, List<StarWarsCharacter> items) {
        super(ctx, 0, items);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_character, parent, false);
        }
        TextView tv = convertView.findViewById(R.id.text1);
        tv.setText(getItem(pos).name);
        return convertView;
    }
}
