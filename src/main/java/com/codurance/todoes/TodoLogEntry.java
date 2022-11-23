package com.codurance.todoes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public final class TodoLogEntry {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String todoId;

    @Column(nullable = false)
    private Instant date;

    @Column(nullable = false)
    private String message;

    public TodoLogEntry(String todoId, Instant date, String message) {
        this.todoId = todoId;
        this.date = date;
        this.message = message;
    }

    public TodoLogEntry() {
    }

    public Instant getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}
