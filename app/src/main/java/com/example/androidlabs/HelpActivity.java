package com.example.nasaimageoftheday;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showHelpDialog();
    }

    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.help))
                .setMessage(getString(R.string.help_message))
                .setPositiveButton("OK", (DialogInterface dialog, int which) -> finish())
                .setCancelable(false)
                .show();
    }
}
