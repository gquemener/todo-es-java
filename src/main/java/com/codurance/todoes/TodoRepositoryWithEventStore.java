package com.codurance.todoes;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TodoRepositoryWithEventStore implements TodoRepository, TodoList {
    private Map<TodoId, List<TodoEvent>> store = new HashMap<>();

    @Override
    public void save(TodoAggregate todo) {
        TodoId id = todo.id();
        if (!store.containsKey(id)) {
            store.put(id, new ArrayList<>());
        }
        for (TodoEvent event : todo.popEvents()) {
            store.get(id).add(event);
        }
    }

    @Override
    public List<TodoReadModel> all() {
        List<TodoReadModel> todoList = new ArrayList<>();
        
        for (Map.Entry<TodoId, List<TodoEvent>> entry : store.entrySet()) {
            for (TodoEvent event : entry.getValue()) {
                if (event instanceof TodoWasCreated todoWasCreated) {
                    todoList.add(new TodoReadModel(todoWasCreated.description(), false));
                }
            }
        }
        
        return todoList; 
    }
}
