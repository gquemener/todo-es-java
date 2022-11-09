package com.codurance.todoes;

import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@SpringBootApplication(scanBasePackages = "com.codurance.todoes")
@RestController
public class TodoesApplication {

	@Autowired
	private TodoRepository repository;

	@Autowired
	private TodoList todoList;

	public static void main(String[] args) {
		SpringApplication.run(TodoesApplication.class, args);
	}

	@GetMapping("/")
	public ModelAndView todos() {
		List<TodoReadModel> todos = todoList.all();

		return new ModelAndView("todos", "todos", todos);
	}

	@PostMapping("/todos")
	public RedirectView createTodo(
			@ModelAttribute CreateTodo createTodo
	) {
		TodoAggregate todo = new TodoAggregate(TodoId.generate(), createTodo.description);
		repository.save(todo);

		return new RedirectView("/", true);
	}

	@GetMapping("/close/{id}")
	public RedirectView closeTodo(
		@PathVariable String id
	) {
		TodoAggregate todo = repository.find(TodoId.fromString(id)).orElseThrow();
		todo.close();
		repository.save(todo);

		return new RedirectView("/", true);
	}
}
