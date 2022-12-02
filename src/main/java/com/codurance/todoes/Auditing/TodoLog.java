package com.codurance.todoes.Auditing;

import java.util.List;

public record TodoLog(List<TodoLogEntry> entries) {
}
