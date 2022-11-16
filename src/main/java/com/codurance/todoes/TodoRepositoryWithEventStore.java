package com.codurance.todoes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.*;

@Component
public class TodoRepositoryWithEventStore implements TodoRepository, TodoList {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static TodoEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
        String eventName = rs.getString("event_name");
        Map<String, Object> map = new HashMap<>();
        map.put("id", rs.getString("event_id"));
        map.put("aggregate_id", rs.getString("aggregate_id"));
        map.put("payload", rs.getString("payload"));

        ZoneId utc = ZoneId.of("UTC");
        map.put(
                "created_at",
                rs.getTimestamp("created_at").toLocalDateTime().atZone(utc)
        );
        map.put("version", rs.getInt("aggregate_version"));

        switch (eventName) {
            case "TodoWasCreated":
                return TodoWasCreated.fromMap(map);

            case "TodoWasClosed":
                return TodoWasClosed.fromMap(map);
        }

        throw new RuntimeException("Could not unserialize event " + eventName);
    }

    @Override
    public void save(TodoAggregate todo) {
        for (TodoEvent event : todo.popEvents()) {
            JSONObject metadata = new JSONObject();
            metadata.put("_aggregate_id", event.aggregateId().toString());
            metadata.put("_aggregate_type", "todo");
            metadata.put("_aggregate_version", event.version());

            ObjectMapper objectMapper = new ObjectMapper();
            String payload;
            try {
                payload = objectMapper.writeValueAsString(event.payload());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            jdbcTemplate.update(
            "INSERT INTO todo_events (event_id, event_name, payload, metadata, created_at) " +
                    "VALUES (?, ?, ?::jsonb, ?::jsonb, ?::timestamp)",
                event.id(),
                event.getClass().getSimpleName(),
                payload,
                metadata.toJSONString(),
                event.createdAt().toLocalDateTime()
            );
        }
    }

    @Override
    public Optional<TodoAggregate> find(TodoId id) {
        List<TodoEvent> history = jdbcTemplate.query(
                "select" +
                        " metadata->>'_aggregate_id' as aggregate_id," +
                        " metadata->>'_aggregate_version' as aggregate_version," +
                        " payload," +
                        " event_name," +
                        " event_id," +
                        " created_at " +
                        "from todo_events te where metadata->>'_aggregate_id' = ? order by \"no\" ;",
                TodoRepositoryWithEventStore::mapRow,
                id.toString()
        );

        return Optional.of(TodoAggregate.replay(
            history
        ));
    }

    @Override
    public List<TodoReadModel> all() {
        List<TodoEvent> events = jdbcTemplate.query(
            "select" +
                    " metadata->>'_aggregate_id' as aggregate_id," +
                    " metadata->>'_aggregate_version' as aggregate_version," +
                    " payload," +
                    " event_name," +
                    " event_id," +
                    " created_at " +
                    "from todo_events te order by \"no\" ;",
            TodoRepositoryWithEventStore::mapRow
        );

        Map<TodoId, TodoReadModel> todos = new LinkedHashMap<>();

        for (TodoEvent event : events) {
            if (event instanceof TodoWasCreated todoWasCreated) {
                todos.put(todoWasCreated.aggregateId(), new TodoReadModel(
                        todoWasCreated.aggregateId().toString(),
                        todoWasCreated.payload().description(),
                        todoWasCreated.createdAt()
                ));
            }

            if (event instanceof TodoWasClosed todoWasClosed) {
                todos.get(todoWasClosed.aggregateId()).setClosed(todoWasClosed.createdAt());
            }
        }

        return todos.values().stream().toList();

    }
}
