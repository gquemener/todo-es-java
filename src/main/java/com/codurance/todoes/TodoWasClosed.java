package com.codurance.todoes;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

public class TodoWasClosed implements TodoEvent<TodoWasClosed.Payload> {
    private UUID id;
    private TodoId aggregateId;
    private ZonedDateTime createdAt;
    private Integer version;
    private Long position;

    public TodoWasClosed(TodoId aggregateId) {
        this.aggregateId = aggregateId;
        this.id = UUID.randomUUID();
        this.createdAt = ZonedDateTime.now(ZoneId.of("UTC"));
    }

    private TodoWasClosed() {
    }

    public static TodoWasClosed fromMap(Map<String, Object> map) {
        TodoWasClosed self = new TodoWasClosed();
        self.aggregateId = TodoId.fromString((String) map.get("aggregate_id"));
        self.id = UUID.fromString((String) map.get("id"));
        self.createdAt = (ZonedDateTime) map.get("created_at");
        self.version = (Integer) map.get("version");
        self.position = (Long) map.get("position");

        return self;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public TodoId aggregateId() {
        return aggregateId;
    }

    @Override
    public Integer version() {
        return version;
    }

    @Override
    public UUID id() {
        return id;
    }

    @Override
    public ZonedDateTime createdAt() {
        return createdAt;
    }

    @Override
    public Payload payload() {
        return new Payload();
    }

    @Override
    public Long position() {
        return position;
    }

    public record Payload() {
    }
}
