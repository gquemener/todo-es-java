package com.codurance.todoes;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class TodoReadModel {
    private final String id;
    private final String description;
    private final ZonedDateTime createdAt;
    private boolean closed;
    private long closedIn;

    public TodoReadModel(String id, String description, ZonedDateTime createdAt) {
        this.id = id;
        this.description = description;
        this.createdAt = createdAt;
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

    public long getClosedIn() {
        return closedIn;
    }

    public void setClosed(ZonedDateTime closedAt) {
        this.closed = true;
        this.closedIn = ChronoUnit.SECONDS.between(createdAt, closedAt);
    }
}
