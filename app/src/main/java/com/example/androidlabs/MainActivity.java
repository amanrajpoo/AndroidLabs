package com.example.nasaimageoftheday;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.androidlabs.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText dateInput;
    private Button fetchButton;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        dateInput = findViewById(R.id.dateInput);
        fetchButton = findViewById(R.id.fetchButton);
        executorService = Executors.newSingleThreadExecutor();

        fetchButton.setOnClickListener(v -> {
            String date = dateInput.getText().toString();
            fetchImageData(date);
        });
    }

    private void fetchImageData(String date) {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        executorService.execute(() -> {
            // Fetch data in background
            ApodResponse finalResponse = NetworkUtils.fetchApodData(date);

            runOnUiThread(() -> {
                progressBar.setVisibility(ProgressBar.GONE);
                if (finalResponse != null) {
                    // Show the fragment with details
                    ImageDetailFragment fragment = ImageDetailFragment.newInstance(
                            finalResponse.getTitle(),
                            finalResponse.getExplanation(),
                            finalResponse.getUrl()
                    );
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, fragment);
                    ft.commit();
                } else {
                    Toast.makeText(this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
