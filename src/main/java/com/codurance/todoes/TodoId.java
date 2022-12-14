package com.codurance.todoes;

import java.util.Objects;
import java.util.UUID;

public class TodoId {
    private final UUID value;

    public TodoId(UUID value) {
        this.value = value;
    }

    public static TodoId generate() {
        return new TodoId(UUID.randomUUID());
    }

    public static TodoId fromString(String id) {
        return new TodoId(UUID.fromString(id));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoId todoId = (TodoId) o;
        return Objects.equals(value, todoId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
