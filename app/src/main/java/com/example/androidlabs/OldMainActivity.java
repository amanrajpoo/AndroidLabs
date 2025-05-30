package com.example.androidlabs;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class OldMainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🔄 Change layout here to test different UIs
        setContentView(R.layout.activity_main_linear);

        // 🔗 Link UI elements by ID
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        checkBox = findViewById(R.id.checkbox);

        // ✅ BUTTON: Copy EditText to TextView and show a Toast
        button.setOnClickListener(v -> {
            String input = editText.getText().toString();
            textView.setText(input);

            Toast.makeText(
                    getApplicationContext(),
                    getResources().getString(R.string.toast_message),
                    Toast.LENGTH_SHORT
            ).show();
        });

        // ✅ CHECKBOX: Show Snackbar with Undo
        checkBox.setOnCheckedChangeListener((cb, isChecked) -> {
            String status = isChecked ? "on" : "off";
            String msg = getResources().getString(R.string.snackbar_text) + " " + status;

            Snackbar.make(cb, msg, Snackbar.LENGTH_LONG)
                    .setAction(getResources().getString(R.string.undo), v -> cb.setChecked(!isChecked))
                    .show();
        });
    }
}
