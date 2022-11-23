package com.codurance.todoes;

public final class TodoLogEntry {
    private final String date;
    private final String message;

    public TodoLogEntry(String date, String message) {
        this.date = date;
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}
