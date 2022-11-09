package com.codurance.todoes;

public class TodoReadModel {
    private final String id;
    private final String description;
    private boolean closed;

    public TodoReadModel(String id, String description) {
        this.id = id;
        this.description = description;
        this.closed = false;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
