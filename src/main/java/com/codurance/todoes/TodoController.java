package com.codurance.todoes;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
public class TodoController {
    @Autowired private QueryGateway queryGateway;
    @Autowired private CommandGateway commandGateway;

    @GetMapping("/")
    public ModelAndView todos() throws ExecutionException, InterruptedException {
        TodoList todoList = queryGateway.query(
                new FetchTodoListQuery(),
                ResponseTypes.instanceOf(TodoList.class)
        ).get();

        return new ModelAndView("todos", "todos", todoList.entries());
    }

    @PostMapping("/todos")
    public RedirectView createTodo(
        @ModelAttribute CreateTodoRequest createTodo
    ) {
        commandGateway.sendAndWait(new CreateTodo(UUID.randomUUID().toString(), createTodo.description()));

        return new RedirectView("/", true);
    }

    @GetMapping("/close/{id}")
    public RedirectView closeTodo(
        @PathVariable String id
    ) {
        commandGateway.sendAndWait(new CloseTodo(id));

        return new RedirectView("/", true);
    }

    @GetMapping("/{id}")
    public ModelAndView showLog(@PathVariable String id) throws ExecutionException, InterruptedException {
        List<TodoLogEntry> entries = queryGateway.query(
            new FetchTodoLogEntryQuery(id),
            ResponseTypes.multipleInstancesOf(TodoLogEntry.class)
        ).get();

        return new ModelAndView("log", "entries", entries);
    }

    private record CreateTodoRequest(String description) {
    }
}
