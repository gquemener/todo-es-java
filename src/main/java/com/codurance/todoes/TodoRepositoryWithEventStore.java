package com.codurance.todoes;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TodoRepositoryWithEventStore implements TodoRepository, TodoList {
    private final Map<TodoId, List<TodoEvent>> store = new HashMap<>();

    @Override
    public void save(TodoAggregate todo) {
        TodoId id = todo.id();
        store.computeIfAbsent(id, k -> new ArrayList<>());
        for (TodoEvent event : todo.popEvents()) {
            store.get(id).add(event);
        }
    }

    @Override
    public Optional<TodoAggregate> find(TodoId id) {
        if (!store.containsKey(id)) {
            return Optional.empty();
        }

        return Optional.of(TodoAggregate.replay(
            store.get(id)
        ));
    }

    @Override
    public List<TodoReadModel> all() {
        Map<TodoId, TodoReadModel> todos = new HashMap<>();
        
        for (Map.Entry<TodoId, List<TodoEvent>> entry : store.entrySet()) {
            for (TodoEvent event : entry.getValue()) {
                if (event instanceof TodoWasCreated todoWasCreated) {
                    todos.put(todoWasCreated.id(), new TodoReadModel(
                        todoWasCreated.id().toString(),
                        todoWasCreated.description()
                    ));
                }

                if (event instanceof TodoWasClosed todoWasClosed) {
                    todos.get(todoWasClosed.id()).setClosed(true);
                }
            }
        }
        
        return todos.values().stream().toList();
    }
}
