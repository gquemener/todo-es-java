package com.codurance.todoes;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class TodoProjection {
    private final List<Todo> todoList = new CopyOnWriteArrayList<>();

    @EventHandler
    public void on(TodoWasCreated evt) {
        Todo todo = new Todo(evt.id(), evt.description(), false);
        todoList.add(todo);
    }

    @EventHandler
    public void on(TodoWasClosed evt) {
        todoList.stream()
                .filter(todo -> todo.getId().equals(evt.id()))
                .findFirst()
                .ifPresent(todo -> {
                    Todo updatedTodo = new Todo(todo.getId(), todo.getDescription(), true);
                    todoList.remove(todo);
                    todoList.add(updatedTodo);
                });
    }

    @QueryHandler
    public List<Todo> fetch(FetchTodoListQuery query) {
        return todoList;
    }
}
