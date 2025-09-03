package com.example.to_do_list.controller;

import com.example.to_do_list.service.TodoService;
import com.example.to_do_list.domain.Todo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    // -------------------- CREATE --------------------
    @PostMapping
    public Todo create(@RequestBody Todo todo) {
        return service.save(todo);
    }

    // -------------------- READ --------------------
    // Read all
    @GetMapping
    public List<Todo> getAll() {
        return service.findAllByOrderByDueDateAsc();
    }

    // Read one
    @GetMapping("/{id}")
    public Todo getOne(@PathVariable Long id) {
        return service.findById(id);
    }

    // -------------------- UPDATE --------------------
    @PutMapping("/{id}/toggle")
    public Todo toggleCompleted(@PathVariable Long id) {
        return service.toggleCompleted(id);
    }

    // -------------------- DELETE --------------------
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

}
