package com.codurance.todoes;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

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
    public List<TodoLogEntry> fetch(FetchTodoLogEntryQuery query) {
        return repository.findAllByTodoId(query.id());
    }
}
