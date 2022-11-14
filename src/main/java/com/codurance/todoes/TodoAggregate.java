package com.codurance.todoes;

import java.util.ArrayList;
import java.util.List;

public class TodoAggregate {

    private final List<TodoEvent> recordedEvents = new ArrayList<>();
    private TodoId id;

    private Integer version = 0;
    private boolean closed;

    private TodoAggregate() {
    }

    public TodoAggregate(TodoId id, String description) {
        recordThat(new TodoWasCreated(id, description));
    }


    public void close() {
        if (closed) {
            throw new RuntimeException("Todo is already closed");
        }

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
        event.setVersion(++version);
        recordedEvents.add(event);
        apply(event);
    }

    private void apply(TodoEvent event) {
        this.version = event.version();
        if (event instanceof TodoWasCreated todoWasCreated) {
            this.id = todoWasCreated.aggregateId();
            this.closed = false;
        }
        if (event instanceof TodoWasClosed todoWasClosed) {
            this.closed = true;
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
