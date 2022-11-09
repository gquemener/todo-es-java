package com.codurance.todoes;

import java.util.UUID;

public class TodoId {
    private final UUID value;

    public TodoId(UUID value) {
        this.value = value;
    }

    public static TodoId generate() {
        return new TodoId(UUID.randomUUID());
    }
}
