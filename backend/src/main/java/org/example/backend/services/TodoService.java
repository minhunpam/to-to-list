package org.example.backend.services;

import org.example.backend.dtos.TodoDto;
import org.example.backend.entities.TodoEntity;
import org.example.backend.pojos.TodoGetResponse;
import org.example.backend.pojos.TodoPostResponse;
import org.example.backend.repositories.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public TodoPostResponse createTodo(TodoDto todoDto) {
        TodoEntity todoEntity = new TodoEntity(todoDto);
        repository.save(todoEntity);
        return new TodoPostResponse(todoDto.getTitle(), todoDto.getDescription());
    }

    public List<TodoGetResponse> getTodos() {
        return repository.findAll()
                .stream()
                .map(todoEntity -> new TodoGetResponse(todoEntity.getId(),
                                                                todoEntity.getTitle(),
                                                                todoEntity.getDescription()))
                .toList();
    }

}
