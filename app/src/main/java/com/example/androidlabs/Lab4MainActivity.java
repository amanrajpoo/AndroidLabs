package com.example.androidlabs;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.widget.SwitchCompat;


public class Lab4MainActivity extends AppCompatActivity {

    List<TodoItem> todoList = new ArrayList<>();
    TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab4_main);

        EditText editText = findViewById(R.id.todoEditText);
        SwitchCompat urgentSwitch = findViewById(R.id.urgentSwitch);
        Button addButton = findViewById(R.id.addButton);
        ListView listView = findViewById(R.id.todoListView);

        adapter = new TodoAdapter(this, todoList);
        listView.setAdapter(adapter);

        addButton.setOnClickListener(v -> {
            String text = editText.getText().toString().trim();
            if (!text.isEmpty()) {
                todoList.add(new TodoItem(text, urgentSwitch.isChecked()));
                adapter.notifyDataSetChanged(); // Refresh the list
                editText.setText(""); // Clear after adding
            }
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(Lab4MainActivity.this)
                    .setTitle(getString(R.string.delete_title))
                    .setMessage(getString(R.string.delete_message) + " " + position)
                    .setPositiveButton(getString(R.string.delete_positive), (dialog, which) -> {
                        todoList.remove(position);
                        adapter.notifyDataSetChanged(); // Refresh list
                    })
                    .setNegativeButton(getString(R.string.delete_negative), null)
                    .show();
            return true;
        });
    }
}
