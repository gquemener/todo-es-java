package com.codurance.todoes;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public record CloseTodo(@TargetAggregateIdentifier String id) {
}
