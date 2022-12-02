package com.codurance.todoes.Todo;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CloseTodo(@TargetAggregateIdentifier String id) {
}
