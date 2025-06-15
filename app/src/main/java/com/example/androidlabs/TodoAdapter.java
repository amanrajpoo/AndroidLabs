package com.example.androidlabs;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TodoAdapter extends BaseAdapter {

    private Context context;
    private List<TodoItem> todoList;

    public TodoAdapter(Context context, List<TodoItem> todoList) {
        this.context = context;
        this.todoList = todoList;
    }

    @Override
    public int getCount() {
        return todoList.size();
    }

    @Override
    public Object getItem(int position) {
        return todoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(R.layout.todo_item, parent, false);
        }

        TextView textView = row.findViewById(R.id.todoItemTextView);
        TodoItem item = todoList.get(position);

        textView.setText(item.getText());

        if (item.isUrgent()) {
            row.setBackgroundColor(Color.RED);
            textView.setTextColor(Color.WHITE);
        } else {
            row.setBackgroundColor(Color.TRANSPARENT);
            textView.setTextColor(Color.BLACK);
        }

        return row;
    }
}
