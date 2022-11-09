package com.codurance.todoes;

public record TodoWasCreated(TodoId id, String description) implements TodoEvent {
}
