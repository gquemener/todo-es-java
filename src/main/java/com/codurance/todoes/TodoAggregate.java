package com.codurance.todoes;

import java.util.ArrayList;
import java.util.List;

public class TodoAggregate {

    private final List<TodoEvent> recordedEvents = new ArrayList<>();
    private TodoId id;

    private TodoAggregate() {
    }

    public TodoAggregate(TodoId id, String description) {
        recordThat(new TodoWasCreated(id, description));
    }


    public void close() {
        recordThat(new TodoWasClosed(id));
    }

    public static TodoAggregate replay(List<TodoEvent> history) {
        TodoAggregate todo = new TodoAggregate();
        for (TodoEvent event : history) {
            todo.apply(event);
        }

        return todo;
    }

    private void recordThat(TodoEvent event) {
        recordedEvents.add(event);
        apply(event);
    }

    private void apply(TodoEvent event) {
        if (event instanceof TodoWasCreated todoWasCreated) {
            this.id = todoWasCreated.id();
        }
    }

    public List<TodoEvent> popEvents() {
        List<TodoEvent> events = this.recordedEvents.stream().toList();
        this.recordedEvents.clear();

        return events;
    }

    public TodoId id() {
        return this.id;
    }
}
