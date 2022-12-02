package com.codurance.todoes.TodoList;

public final class Todo {
    private final String id;
    private final String description;
    private final String duration;

    public Todo(String id, String description, String duration) {
        this.id = id;
        this.description = description;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }
}
