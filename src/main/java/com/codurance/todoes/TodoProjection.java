package com.codurance.todoes;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class TodoProjection {
    private final List<Todo> todoList = new CopyOnWriteArrayList<>();

    @EventHandler
    public void on(
        TodoWasCreated evt,
        @Timestamp Instant occurredAt
    ) {
        Todo todo = new Todo(evt.id(), evt.description(), occurredAt);
        todoList.add(todo);
    }

    @EventHandler
    public void on(TodoWasClosed evt, @Timestamp Instant occurredAt) {
        todoList.stream()
                .filter(todo -> todo.getId().equals(evt.id()))
                .findFirst()
                .ifPresent(todo -> {
                    Todo updatedTodo = todo.close(occurredAt);
                    todoList.remove(todo);
                    todoList.add(updatedTodo);
                });
    }

    @QueryHandler
    public List<Todo> fetch(FetchTodoListQuery query) {
        return todoList;
    }
}
