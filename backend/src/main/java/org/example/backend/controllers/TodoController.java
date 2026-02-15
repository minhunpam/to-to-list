package org.example.backend.controllers;

import jakarta.validation.Valid;
import org.example.backend.dtos.TodoDto;
import org.example.backend.pojos.TodoResponse;
import org.example.backend.services.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/todos")
@CrossOrigin(origins = "http://127.0.0.1:5501")
@RestController
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody TodoDto todoDto) {
        TodoResponse created = service.createTodo(todoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

}
