package com.codurance.todoes;

import java.util.Objects;

public final class Todo {
    private final String id;
    private final String description;
    private final boolean closed;

    public Todo(String id, String description, boolean closed) {
        this.id = id;
        this.description = description;
        this.closed = closed;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean closed() {
        return closed;
    }

    public String getDuration() {
        return closed ? "y" : null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Todo) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.description, that.description) &&
                this.closed == that.closed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, closed);
    }

    @Override
    public String toString() {
        return "Todo[" +
                "id=" + id + ", " +
                "description=" + description + ", " +
                "closed=" + closed + ']';
    }

}
