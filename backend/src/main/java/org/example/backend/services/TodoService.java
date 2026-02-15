package org.example.backend.services;

import org.example.backend.dtos.TodoDto;
import org.example.backend.entities.TodoEntity;
import org.example.backend.pojos.TodoResponse;
import org.example.backend.repositories.TodoRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public TodoResponse createTodo(TodoDto todoDto) {
        TodoEntity todoEntity = new TodoEntity(todoDto);
        repository.save(todoEntity);
        return new TodoResponse(todoDto.getTitle(), todoDto.getDescription());
    }

}
