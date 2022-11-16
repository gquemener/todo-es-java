package com.codurance.todoes;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface TodoEvent<T> {
    TodoId aggregateId();

    void setVersion(Integer version);
    Integer version();

    UUID id();

    ZonedDateTime createdAt();

    T payload();
}
