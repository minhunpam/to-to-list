package com.example.to_do_list.controller;

import com.example.to_do_list.DTO.TodoRequest;
import com.example.to_do_list.DTO.TodoResponse;
import com.example.to_do_list.service.TodoService;
import com.example.to_do_list.domain.Todo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TodoResponse> create(@Valid @RequestBody TodoRequest todoRequest,
                                               UriComponentsBuilder uri)
    {
        TodoResponse created_response = service.create(todoRequest);

        URI location = uri.path("/api/todos/{id}")
                .build(created_response.id());

        return ResponseEntity.created(location).
                body(created_response);
    }

    @GetMapping
    public List<TodoResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public TodoResponse getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PutMapping("/{id}/toggle")
    public TodoResponse toggleCompleted(@PathVariable Long id) {
        return service.toggleCompleted(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

}
