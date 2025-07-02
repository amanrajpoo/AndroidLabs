package com.example.androidlabs;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class Lab6MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageView pauseOverlay;
    private boolean isPaused = false;
    private final Handler handler = new Handler();
    private final int IMAGE_DELAY = 5000; // 5 seconds

    private final Runnable imageUpdater = new Runnable() {
        @Override
        public void run() {
            if (!isPaused) {
                loadRandomCat();
            }
            handler.postDelayed(this, IMAGE_DELAY);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab6);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imageView = findViewById(R.id.imageView);
        pauseOverlay = findViewById(R.id.pauseOverlay);

        pauseOverlay.setOnClickListener(v -> {
            isPaused = !isPaused;
            pauseOverlay.setImageResource(isPaused ? R.drawable.ic_play : R.drawable.ic_pause);
        });

        loadRandomCat();
        handler.postDelayed(imageUpdater, IMAGE_DELAY);
    }

    private void loadRandomCat() {
        Glide.with(this)
                .load("https://cataas.com/cat?timestamp=" + System.currentTimeMillis()) // avoid caching
                .into(imageView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(imageUpdater);
    }
}
