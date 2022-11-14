package com.codurance.todoes;

import org.json.simple.JSONObject;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface TodoEvent {
    TodoId aggregateId();

    void setVersion(Integer version);
    Integer version();

    UUID id();

    JSONObject payload();

    ZonedDateTime createdAt();
}
