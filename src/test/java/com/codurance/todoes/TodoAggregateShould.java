package com.codurance.todoes;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TodoAggregateTest {
    private AggregateTestFixture<TodoAggregate> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(TodoAggregate.class);
    }

    @Test
    void testCreateTodo() {
        fixture
            .when(new CreateTodo("ID", "Do that!"))
            .expectSuccessfulHandlerExecution()
            .expectEvents(new TodoWasCreated("ID", "Do that!"));
    }

    @Test
    void testCloseTodo() {
        fixture
            .given(new TodoWasCreated("ID", "Do that!"))
            .when(new CloseTodo("ID"))
            .expectSuccessfulHandlerExecution()
            .expectEvents(new TodoWasClosed("ID"));
    }

    @Test
    void testCloseAlreadyClosedTodo() {
        fixture
            .given(
                new TodoWasCreated("ID", "Do that!"),
                new TodoWasClosed("ID")
            )
            .when(new CloseTodo("ID"))
            .expectException(IllegalStateException.class);
    }
}
