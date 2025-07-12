package com.example.androidlabs;

public class TodoItem {
    private String text;
    private boolean urgent;
    private String name;

    public TodoItem(String text, boolean urgent, String name) {
        this.text = text;
        this.urgent = urgent;
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public String getName() {
        return name;
    }
}

