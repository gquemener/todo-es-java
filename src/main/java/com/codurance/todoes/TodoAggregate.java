package com.codurance.todoes;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class TodoAggregate {
    @AggregateIdentifier
    private String id;

    private boolean closed;

    public TodoAggregate() {
    }

    @CommandHandler
    public TodoAggregate(CreateTodo cmd) {
        AggregateLifecycle.apply(new TodoWasCreated(cmd.id(), cmd.description()));
    }

    @EventSourcingHandler
    public void on(TodoWasCreated evt) {
        id = evt.id();
        closed = false;
    }

    @CommandHandler
    public void close(CloseTodo cmd) {
        if (closed) throw new IllegalStateException("Already closed");

        AggregateLifecycle.apply(new TodoWasClosed(id));
    }

    @EventSourcingHandler
    public void on(TodoWasClosed evt) {
        closed = true;
    }
}
