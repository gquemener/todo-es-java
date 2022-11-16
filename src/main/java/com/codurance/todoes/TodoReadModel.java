package com.codurance.todoes;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class TodoReadModel {
    private final String id;
    private final String description;
    private final String duration;

    public TodoReadModel(String id, String description, String duration) {
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
