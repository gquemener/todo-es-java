package com.codurance.todoes;

public class TodoReadModel {
    private final String description;
    private final boolean closed;

    public TodoReadModel(String description, boolean closed) {
        this.description = description;
        this.closed = closed;
    }

    public String getDescription() {
        return description;
    }

    public boolean isClosed() {
        return closed;
    }
}
