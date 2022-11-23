package com.codurance.todoes;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoLogEntryRepository extends CrudRepository<TodoLogEntry, String> {
    List<TodoLogEntry> findAllByTodoId(String todoId);
}
