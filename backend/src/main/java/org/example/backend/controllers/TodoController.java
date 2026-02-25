package org.example.backend.controllers;

import jakarta.validation.Valid;
import org.example.backend.ParameterValidator;
import org.example.backend.dtos.TodoDto;
import org.example.backend.exception.NotFoundException;
import org.example.backend.pojos.TodoDeleteResponse;
import org.example.backend.pojos.TodoGetResponse;
import org.example.backend.pojos.TodoPatchResponse;
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

    @GetMapping("/{id}")
    public ResponseEntity<TodoGetResponse> getTodo(@PathVariable long id) {
        ParameterValidator.validateID(id);

        TodoGetResponse todoGetResponse = service.getTodo(id);
        if (todoGetResponse == null) throw new NotFoundException("[FAILED] To-do List not found!");

        return ResponseEntity.status(HttpStatus.OK).body(todoGetResponse);
    }

    @PostMapping
    public ResponseEntity<TodoPostResponse> createTodo(@Valid @RequestBody TodoDto todoDto) {
        TodoPostResponse created = service.createTodo(todoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TodoDeleteResponse> deleteTodo(@PathVariable Long id) {
        ParameterValidator.validateID(id);

        TodoGetResponse todoGetResponse = service.getTodo(id);
        if (todoGetResponse == null) throw new NotFoundException("[FAILED] To-do List not found!");

        TodoDeleteResponse deleted = new TodoDeleteResponse(
                todoGetResponse.getId(),
                todoGetResponse.getTitle(),
                todoGetResponse.getDescription()
        );
        service.deleteTodo(id);

        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoPatchResponse> patchTodo(@PathVariable Long id, @Valid @RequestBody TodoDto todoDto) {
        ParameterValidator.validateID(id);

        TodoGetResponse todoGetResponse = service.getTodo(id);
        if (todoGetResponse == null) throw new NotFoundException("[FAILED] To-do List not found!");

        TodoPatchResponse patched = new TodoPatchResponse(
                id,
                todoDto.getTitle(),
                todoDto.getDescription()
        );

        service.patchTodo(patched);

        return ResponseEntity.status(HttpStatus.OK).body(patched);
    }

}
