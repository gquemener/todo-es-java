package com.codurance.todoes;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Component
public class TodoLogEntryProjection {
    private final JdbcTemplate jdbcTemplate;

    public TodoLogEntryProjection(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventHandler
    public void on(
        TodoWasCreated evt,
        @Timestamp Instant occurredAt
    ) {
        jdbcTemplate.update(
        "insert into todo_log_read_model (todo_id, occurred_at, message) values (?, ?, ?)",
        evt.id(),
            LocalDateTime.ofInstant(occurredAt, ZoneOffset.UTC),
            "Todo was created"
        );
    }

    @EventHandler
    public void on(TodoWasClosed evt, @Timestamp Instant occurredAt) {
        jdbcTemplate.update(
            "insert into todo_log_read_model (todo_id, occurred_at, message) values (?, ?, ?)",
            evt.id(),
            LocalDateTime.ofInstant(occurredAt, ZoneOffset.UTC),
            "Todo was closed"
        );
    }

    @QueryHandler
    public List<TodoLogEntry> fetch(FetchTodoLogEntryQuery query) {
        return jdbcTemplate.query(
            "SELECT * FROM todo_log_read_model WHERE todo_id = ? order by occurred_at asc",
            (rs, rowNum) -> new TodoLogEntry(
                rs.getString("occurred_at"),
                rs.getString("message")
            ),
            query.id()
        );
    }
}
