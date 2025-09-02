package com.example.to_do_list.service;

import com.example.to_do_list.repository.TodoRepository;
import com.example.to_do_list.domain.Todo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.*;

@Service
public class TodoService {

    private final TodoRepository repo;

    public TodoService(TodoRepository repo) {
        this.repo = repo;
    }

    // Create and Update
    // -------------------- CREATE/UPDATE --------------------
    @Transactional
    public Todo save(Todo todo) {
        if (todo.getTitle() == null || todo.getTitle().isBlank()) {
            throw new BusinessException("Title cannot be empty");
        }

        if (todo.getTitle().length() > 100) {
            throw new BusinessException("Title is too long");
        }

        if (todo.getDueDate() != null && todo.getDueDate().isBefore(LocalDate.now())) {
            throw new BusinessException("Due date cannot be in the past");
        }

        return repo.save(todo);
    }

    @Transactional
    public Todo toggleCompleted(Long id) {
        Todo todo = repo.findById(id)
                .orElseThrow(() -> new BusinessException("Todo id: " + id + " is not found"));

        // Flip the `completed` status
        todo.setCompleted(!todo.isCompleted());
        return repo.save(todo);
    }

    // -------------------- READ --------------------
    // Read all
    @Transactional(readOnly = true)
    public List<Todo> findAllByOrderByDueDateAsc() {
        return repo.findAllByOrderByDueDateAsc();
    }

    // Read one
    @Transactional(readOnly = true)
    public Todo findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new BusinessException("Todo id: " + id + " is not found"));
    }

    // -------------------- DELETE ---------------------
    @Transactional
    public void deleteById(Long id) {
        repo.deleteById(id);
    }



}
