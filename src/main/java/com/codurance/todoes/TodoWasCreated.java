package com.codurance.todoes;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

public final class TodoWasCreated implements TodoEvent<TodoWasCreated.Payload> {
    private Payload payload;
    private TodoId aggregateId;
    private UUID id;
    private ZonedDateTime createdAt;
    private Integer version;
    private Long position;

    public TodoWasCreated(TodoId aggregateId, String description) {
        this.payload = new Payload(aggregateId.toString(), description);
        this.aggregateId = aggregateId;
        this.id = UUID.randomUUID();
        this.createdAt = ZonedDateTime.now(ZoneId.of("UTC"));
    }

    private TodoWasCreated() {
    }

    public static TodoWasCreated fromMap(Map<String, Object> map) {
        TodoWasCreated self = new TodoWasCreated();
        self.aggregateId = TodoId.fromString((String) map.get("aggregate_id"));
        JSONObject payload;
        try {
            payload = (JSONObject) new JSONParser().parse((String) map.get("payload"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        self.payload = new Payload(
            self.aggregateId.toString(),
            (String) payload.get("description")
        );
        self.id = UUID.fromString((String) map.get("id"));
        self.createdAt = (ZonedDateTime) map.get("created_at");
        self.version = (Integer) map.get("version");
        self.position = (Long) map.get("position");

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
    public ZonedDateTime createdAt() {
        return createdAt;
    }

    @Override
    public Payload payload() {
        return payload;
    }

    @Override
    public Long position() {
        return position;
    }

    public record Payload(String id, String description) {
    }
}
