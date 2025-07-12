package com.example.androidlabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class DetailsFragment extends Fragment {
    public static final String ARG_NAME   = "name";
    public static final String ARG_HEIGHT = "height";
    public static final String ARG_MASS   = "mass";

    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup container, Bundle saved) {
        View root = inf.inflate(R.layout.fragment_details, container, false);
        Bundle args = getArguments();
        if (args != null) {
            ((TextView)root.findViewById(R.id.tvName)).setText("Name: "   + args.getString(ARG_NAME));
            ((TextView)root.findViewById(R.id.tvHeight)).setText("Height: "+ args.getString(ARG_HEIGHT));
            ((TextView)root.findViewById(R.id.tvMass)).setText("Mass: "   + args.getString(ARG_MASS));
        }
        return root;
    }
}
