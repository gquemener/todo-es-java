package com.codurance.todoes;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TodoListFinder implements TodoList {
    private final JdbcTemplate jdbcTemplate;

    public TodoListFinder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TodoReadModel> all() {
        return jdbcTemplate.query(
                "SELECT * FROM todo_list_read_model order by closed_at asc, created_at asc",
                (rs, rowNum) -> new TodoReadModel(
                    rs.getString("id"),
                    rs.getString("description"),
                    rs.getString("duration")
                )
        );
    }
}
