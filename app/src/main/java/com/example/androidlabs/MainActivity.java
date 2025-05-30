package com.example.androidlabs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    Button btnNext;
    SharedPreferences prefs;
    final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        btnNext = findViewById(R.id.btnNext);

        prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedName = prefs.getString("user_name", "");
        etName.setText(savedName);

        btnNext.setOnClickListener(v -> {
            String name = etName.getText().toString();
            Intent intent = new Intent(MainActivity.this, NameActivity.class);
            intent.putExtra("user_name", name);
            startActivityForResult(intent, REQUEST_CODE);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user_name", etName.getText().toString());
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == 0) {
                etName.setText("");
            } else if (resultCode == 1) {
                finish();
            }
        }
    }
}
