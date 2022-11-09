package com.codurance.todoes;

import java.util.ArrayList;
import java.util.List;

public class TodoAggregate {

    private final List<TodoEvent> recordedEvents = new ArrayList<>();
    private final TodoId id;

    public TodoAggregate(TodoId id, String description) {
        this.id = id;
        recordedEvents.add(new TodoWasCreated(id, description));
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
