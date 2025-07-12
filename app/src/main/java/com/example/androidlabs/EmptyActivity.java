package com.example.androidlabs;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class EmptyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_empty);

        if (saved == null) {
            Bundle args = getIntent().getExtras();
            DetailsFragment frag = new DetailsFragment();
            frag.setArguments(args);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.empty_container, frag);
            ft.commit();
        }
    }
}
