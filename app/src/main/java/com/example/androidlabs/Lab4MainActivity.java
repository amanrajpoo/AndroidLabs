package com.example.androidlabs;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;
import java.util.List;

public class Lab4MainActivity extends AppCompatActivity {
    TodoDatabaseHelper dbHelper;
    SQLiteDatabase db;
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

        // Initialize DB
        dbHelper = new TodoDatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        // Load todos from DB
        Cursor cursor = db.query(TodoDatabaseHelper.TABLE_NAME, null, null, null, null, null, null);

        // Print cursor debug info BEFORE using it
        printCursor(cursor);

        // Reset cursor so we can loop through it
        cursor.moveToPosition(-1);

        while (cursor.moveToNext()) {
            String text = cursor.getString(cursor.getColumnIndexOrThrow(TodoDatabaseHelper.COL_TEXT));
            boolean isUrgent = cursor.getInt(cursor.getColumnIndexOrThrow(TodoDatabaseHelper.COL_URGENT)) == 1;
            todoList.add(new TodoItem(text, isUrgent));
        }
        cursor.close();

        // Setup Adapter
        adapter = new TodoAdapter(this, todoList);
        listView.setAdapter(adapter);

        // Add new todo
        addButton.setOnClickListener(v -> {
            String text = editText.getText().toString().trim();
            boolean isUrgent = urgentSwitch.isChecked();

            if (!text.isEmpty()) {
                // Add to list
                TodoItem item = new TodoItem(text, isUrgent);
                todoList.add(item);
                adapter.notifyDataSetChanged();
                editText.setText("");

                // Add to DB
                ContentValues values = new ContentValues();
                values.put(TodoDatabaseHelper.COL_TEXT, text);
                values.put(TodoDatabaseHelper.COL_URGENT, isUrgent ? 1 : 0);
                db.insert(TodoDatabaseHelper.TABLE_NAME, null, values);
            }
        });

        // Delete todo on long press
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            TodoItem item = todoList.get(position);

            new AlertDialog.Builder(Lab4MainActivity.this)
                    .setTitle(getString(R.string.delete_title))
                    .setMessage(getString(R.string.delete_message) + " " + position)
                    .setPositiveButton(getString(R.string.delete_positive), (dialog, which) -> {
                        // Delete from DB
                        db.delete(TodoDatabaseHelper.TABLE_NAME,
                                TodoDatabaseHelper.COL_TEXT + " = ? AND " + TodoDatabaseHelper.COL_URGENT + " = ?",
                                new String[]{item.getText(), item.isUrgent() ? "1" : "0"});

                        // Remove from list
                        todoList.remove(position);
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(getString(R.string.delete_negative), null)
                    .show();
            return true;
        });
    }

    // Debug info about the DB contents
    private void printCursor(Cursor c) {
        c.moveToPosition(-1);          // <-- rewind cursor to BEFORE first row
        Log.d("DEBUG", "DB Version: " + db.getVersion());
        Log.d("DEBUG", "Column Count: " + c.getColumnCount());

        for (int i = 0; i < c.getColumnCount(); i++) {
            Log.d("DEBUG", "Column: " + c.getColumnName(i));
        }

        Log.d("DEBUG", "Row Count: " + c.getCount());

        while (c.moveToNext()) {
            String text = c.getString(c.getColumnIndexOrThrow(TodoDatabaseHelper.COL_TEXT));
            int urgent = c.getInt(c.getColumnIndexOrThrow(TodoDatabaseHelper.COL_URGENT));
            Log.d("DEBUG", "Row: " + text + " | Urgent: " + urgent);
        }
    }
}
