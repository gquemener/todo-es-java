package com.codurance.todoes.Auditing;

import com.codurance.todoes.Todo.TodoWasClosed;
import com.codurance.todoes.Todo.TodoWasCreated;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TodoLogEntryProjection {
    private final TodoLogEntryRepository repository;

    public TodoLogEntryProjection(TodoLogEntryRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(
        TodoWasCreated evt,
        @Timestamp Instant occurredAt
    ) {
        repository.save(new TodoLogEntry(evt.id(), occurredAt, "Todo was created"));
    }

    @EventHandler
    public void on(TodoWasClosed evt, @Timestamp Instant occurredAt) {
        repository.save(new TodoLogEntry(evt.id(), occurredAt, "Todo was closed"));
    }

    @QueryHandler
    public TodoLog fetch(FetchTodoLogEntryQuery query) {
        return new TodoLog(repository.findAllByTodoId(query.id()));
    }
}
