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
public class TodoProjection {
    private final JdbcTemplate jdbcTemplate;

    public TodoProjection(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventHandler
    public void on(
        TodoWasCreated evt,
        @Timestamp Instant occurredAt
    ) {
        jdbcTemplate.update(
            "insert into todo_list_read_model (id, description, created_at) values (?, ?, ?)",
            evt.id(),
            evt.description(),
            LocalDateTime.ofInstant(occurredAt, ZoneOffset.UTC)
        );
    }

    @EventHandler
    public void on(TodoWasClosed evt, @Timestamp Instant occurredAt) {
        jdbcTemplate.update(
            "update todo_list_read_model set closed_at = ? where id = ?",
            LocalDateTime.ofInstant(occurredAt, ZoneOffset.UTC),
            evt.id()
        );
    }

    @QueryHandler
    public List<Todo> fetch(FetchTodoListQuery query) {
        return jdbcTemplate.query(
            "SELECT * FROM todo_list_read_model order by closed_at asc, created_at asc",
            (rs, rowNum) -> new Todo(
                rs.getString("id"),
                rs.getString("description"),
                rs.getString("duration")
            )
        );
    }
}
