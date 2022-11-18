package com.codurance.todoes;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CloseTodo(@TargetAggregateIdentifier String id) {
}
