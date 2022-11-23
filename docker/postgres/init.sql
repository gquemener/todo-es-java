CREATE TABLE todo_list_read_model (
    id char(36),
    description text,
    created_at TIMESTAMP(6),
    closed_at TIMESTAMP(6),
    duration interval generated always as ( age(closed_at, created_at) ) stored
);
