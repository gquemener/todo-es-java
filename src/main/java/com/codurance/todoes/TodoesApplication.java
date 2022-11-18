package com.codurance.todoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.codurance.todoes")
public class TodoesApplication {
	public static void main(String[] args) {
		SpringApplication.run(TodoesApplication.class, args);
	}
}
