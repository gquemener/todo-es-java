package com.codurance.todoes;

public interface TodoRepository {
    void save(TodoAggregate todo);
}
