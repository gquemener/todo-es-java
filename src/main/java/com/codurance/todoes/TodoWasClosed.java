package com.codurance.todoes;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

public class TodoWasClosed implements TodoEvent {
    private UUID id;
    private TodoId aggregateId;
    private ZonedDateTime createdAt;
    private Integer version;

    public TodoWasClosed(TodoId aggregateId) {
        this.aggregateId = aggregateId;
        this.id = UUID.randomUUID();
        this.createdAt = ZonedDateTime.now(ZoneId.of("UTC"));
    }

    private TodoWasClosed() {
    }

    public static TodoEvent fromMap(Map<String, Object> map) {
        TodoWasClosed self = new TodoWasClosed();
        self.aggregateId = TodoId.fromString((String) map.get("aggregate_id"));
        self.id = UUID.fromString((String) map.get("id"));
        self.createdAt = (ZonedDateTime) map.get("created_at");
        self.version = (Integer) map.get("version");

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
    public JSONObject payload() {
        return new JSONObject();
    }

    @Override
    public ZonedDateTime createdAt() {
        return createdAt;
    }
}
