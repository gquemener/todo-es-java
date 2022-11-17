package com.codurance.todoes;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.responsetypes.ResponseTypes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@SpringBootApplication(scanBasePackages = "com.codurance.todoes")
@RestController
public class TodoesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoesApplication.class, args);
	}

	@GetMapping("/")
	public ModelAndView todos(
		QueryGateway queryGateway
	) throws ExecutionException, InterruptedException {
		List<Todo> todos = queryGateway.query(
			new FetchTodoListQuery(),
			ResponseTypes.multipleInstancesOf(Todo.class)
		).get();

		return new ModelAndView("todos", "todos", todos);
	}

	@PostMapping("/todos")
	public RedirectView createTodo(
		@ModelAttribute CreateTodoRequest createTodo,
		CommandGateway commandGateway
	) {
		commandGateway.sendAndWait(new CreateTodo(UUID.randomUUID().toString(), createTodo.description()));

		return new RedirectView("/", true);
	}

	@GetMapping("/close/{id}")
	public RedirectView closeTodo(
		@PathVariable String id,
		CommandGateway commandGateway
	) {
		commandGateway.sendAndWait(new CloseTodo(id));

		return new RedirectView("/", true);
	}

	private record CreateTodoRequest(String description) {
	}
}
