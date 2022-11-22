package com.codurance.todoes;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public final class Todo {
    private final String id;
    private final String description;
    private final Instant openedAt;
    private Instant closedAt;

    public Todo(String id, String description, Instant openedAt) {
        this.id = id;
        this.description = description;
        this.openedAt = openedAt;
    }

    public Todo close(Instant closedAt) {
        Todo todo = new Todo(this.id, this.description, this.openedAt);
        todo.closedAt = closedAt;

        return todo;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return closedAt != null ? Duration.between(openedAt, closedAt).toString() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return id.equals(todo.id) && description.equals(todo.description) && openedAt.equals(todo.openedAt) && Objects.equals(closedAt, todo.closedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, openedAt, closedAt);
    }
}
