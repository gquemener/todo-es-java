package com.codurance.todoes;

import java.util.Optional;

public interface TodoRepository {
    void save(TodoAggregate todo);

    Optional<TodoAggregate> find(TodoId id);
}
