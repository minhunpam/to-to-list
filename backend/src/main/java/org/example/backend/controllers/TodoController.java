package org.example.backend.controllers;

import jakarta.validation.Valid;
import org.example.backend.dtos.TodoDto;
import org.example.backend.pojos.TodoGetResponse;
import org.example.backend.pojos.TodoPostResponse;
import org.example.backend.services.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/todos")
@CrossOrigin(origins = {
        "http://127.0.0.1:5501",
        "http://localhost:5501"
})
@RestController
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TodoGetResponse>> getTodos() {
        List<TodoGetResponse> todoGetResponses = service.getTodos();
        return ResponseEntity.status(HttpStatus.OK).body(todoGetResponses);
    }

    @PostMapping
    public ResponseEntity<TodoPostResponse> createTodo(@Valid @RequestBody TodoDto todoDto) {
        TodoPostResponse created = service.createTodo(todoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

}
