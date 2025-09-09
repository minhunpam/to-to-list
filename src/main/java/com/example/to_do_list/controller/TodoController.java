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

    // -------------------- CREATE --------------------
    /**
     * Create a new Todo item.
     *
     * @param todoRequest The request body containing the details of the Todo item to be created.
     * @param uri         UriComponentsBuilder to help build the URI of the created resource.
     *                    This parameter is automatically injected by Spring.
     * @return ResponseEntity containing the created TodoResponse and the location header.
     */
    @PostMapping
    public ResponseEntity<TodoResponse> create(@Valid @RequestBody TodoRequest todoRequest,
                                               UriComponentsBuilder uri) {
        TodoResponse created_response = service.create(todoRequest);

        // start building a URI from the base URI (e.g., http://localhost:8080)
        // then append the path and replace {id} with the actual id of the created resource
        URI location = uri.path("/api/todos/{id}")
                .build(created_response.id());

        // Create a builder with a CREATED (201) status and location header set to the given URI
        // then set the body to the created_response object
        return ResponseEntity.created(location).
                body(created_response);
    }

    // -------------------- READ --------------------
    // Read all
    @GetMapping
    public List<TodoResponse> getAll() {
        return service.getAll();
    }

    // Read one
    @GetMapping("/{id}")
    public TodoResponse getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    // -------------------- UPDATE --------------------
    @PutMapping("/{id}/toggle")
    public TodoResponse toggleCompleted(@PathVariable Long id) {
        return service.toggleCompleted(id);
    }

    // -------------------- DELETE --------------------
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

}
