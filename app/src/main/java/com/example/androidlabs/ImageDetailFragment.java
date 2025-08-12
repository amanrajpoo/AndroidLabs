package com.example.nasaimageoftheday;

import static com.example.androidlabs.R.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.androidlabs.R;

public class ImageDetailFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_EXPLANATION = "explanation";
    private static final String ARG_URL = "url";

    public static ImageDetailFragment newInstance(String title, String explanation, String url) {
        ImageDetailFragment fragment = new ImageDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_EXPLANATION, explanation);
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_detail, container, false);

        TextView titleView = view.findViewById(R.id.titleView);
        TextView explanationView = view.findViewById(R.id.explanationView);
        ImageView imageView = view.findViewById(R.id.imageView);

        Bundle args = getArguments();
        if (args != null) {
            titleView.setText(args.getString(ARG_TITLE));
            explanationView.setText(args.getString(ARG_EXPLANATION));
            Glide.with(this)
                    .load(args.getString(ARG_URL))
                    .into(imageView);
        }

        return view;
    }
}
