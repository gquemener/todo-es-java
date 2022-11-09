package com.codurance.todoes;

public record TodoWasClosed(TodoId id) implements TodoEvent {
}
