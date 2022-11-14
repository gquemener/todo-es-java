package com.codurance.todoes;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

public final class TodoWasCreated implements TodoEvent {
    private TodoId aggregateId;
    private String description;
    private UUID id;
    private ZonedDateTime createdAt;
    private Integer version;

    public TodoWasCreated(TodoId aggregateId, String description) {
        this.aggregateId = aggregateId;
        this.description = description;
        this.id = UUID.randomUUID();
        this.createdAt = ZonedDateTime.now(ZoneId.of("UTC"));
    }

    private TodoWasCreated() {
    }

    public static TodoWasCreated fromMap(Map<String, Object> map) {
        TodoWasCreated self = new TodoWasCreated();
        self.aggregateId = TodoId.fromString((String) map.get("aggregate_id"));
        JSONObject payload = null;
        try {
            payload = (JSONObject) new JSONParser().parse((String) map.get("payload"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        self.description = (String) payload.get("description");
        self.id = UUID.fromString((String) map.get("id"));
        self.createdAt = (ZonedDateTime) map.get("created_at");
        self.version = (Integer) map.get("version");

        return self;
    }

    public TodoId aggregateId() {
        return aggregateId;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
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
        JSONObject json = new JSONObject();
        json.put("id", aggregateId.toString());
        json.put("description", description);

        return json;
    }

    @Override
    public ZonedDateTime createdAt() {
        return createdAt;
    }

    public String description() {
        return description;
    }
}
