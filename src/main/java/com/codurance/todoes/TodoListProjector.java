package com.codurance.todoes;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class TodoListProjector {
    private final TodoRepositoryWithEventStore eventStore;
    private final JdbcTemplate jdbcTemplate;

    public TodoListProjector(
            TodoRepositoryWithEventStore eventStore,
            JdbcTemplate jdbcTemplate
    ) {
        this.eventStore = eventStore;
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Async
    public void run() throws InterruptedException {
        jdbcTemplate.update("truncate todo_list_read_model");
        Long position = 0L;
        while (true) {
            for (TodoEvent event : this.eventStore.read(position)) {
                if (event instanceof TodoWasCreated todoWasCreated) {
                    insertTodo(
                        todoWasCreated.aggregateId().toString(),
                        todoWasCreated.payload().description(),
                        todoWasCreated.createdAt()
                    );
                }

                if (event instanceof TodoWasClosed todoWasClosed) {
                    closeTodo(
                        todoWasClosed.aggregateId().toString(),
                        todoWasClosed.createdAt()
                    );
                }

                position = event.position();
            }
            Thread.sleep(1000);
        }
    }

    private void insertTodo(String aggregateId, String description, ZonedDateTime createdAt) {
        jdbcTemplate.update(
            "insert into todo_list_read_model (id, description, created_at) values (?, ?, ?)",
            aggregateId,
            description,
            createdAt.toLocalDateTime()
        );
    }

    private void closeTodo(String aggregateId, ZonedDateTime closedAt) {
        jdbcTemplate.update(
            "update todo_list_read_model set closed_at = ? where id = ?",
            closedAt.toLocalDateTime(),
            aggregateId
        );
    }
}
