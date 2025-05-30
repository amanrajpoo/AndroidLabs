package com.example.androidlabs;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class NameActivity extends AppCompatActivity {

    TextView tvWelcome;
    Button btnThankYou, btnDontCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnThankYou = findViewById(R.id.btnThankYou);
        btnDontCall = findViewById(R.id.btnDontCall);

        // Get name from intent
        String name = getIntent().getStringExtra("user_name");
        tvWelcome.setText("Welcome " + name + "!");

        btnThankYou.setOnClickListener(v -> {
            setResult(1);
            finish();
        });

        btnDontCall.setOnClickListener(v -> {
            setResult(0);
            finish();
        });
    }
}
