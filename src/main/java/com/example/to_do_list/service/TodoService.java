package com.example.to_do_list.service;

import com.example.to_do_list.exception.BusinessException;
import com.example.to_do_list.repository.TodoRepository;
import com.example.to_do_list.domain.Todo;
import com.example.to_do_list.DTO.TodoResponse;
import com.example.to_do_list.DTO.TodoRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.*;

@Service
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository repo;

    public TodoService(TodoRepository repo) {
        this.repo = repo;
    }

    // Create and Update
    // -------------------- CREATE/UPDATE --------------------
    /**
     * Create a new Todo item based on the provided TodoRequest.
     * Validates the inputs (title, due date, priority, completed) and throws BusinessException for invalid data.
     *
     * @param todoRequest The request object containing details for the new Todo item.
     * @return A TodoResponse object representing the created Todo item.
     * @throws BusinessException if validation fails (e.g., empty title, title too long, invalid priority).
     */
    @Transactional
    public TodoResponse create(TodoRequest todoRequest) {
        Todo todo = new Todo();

        // Title Validation
        if (todoRequest.title() == null || todoRequest.title().isBlank()) {
            throw new BusinessException("Title cannot be empty");
        }
        if (todoRequest.title().length() > 100) {
            throw new BusinessException("Title cannot exceed 100 characters");
        }
        todo.setTitle(todoRequest.title());

        // Due Date Validation
        if (todoRequest.dueDate() != null) {
            todo.setDueDate(todoRequest.dueDate());
        }

        // Priority Validation
        if (todoRequest.priority() != null) {
            try {
                todo.setPriority(Todo.Priority.valueOf(todoRequest.priority().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new BusinessException("Invalid priority value. Allowed values are: LOW, MEDIUM, HIGH");
            }
        }

        // Completed field validation
        if (todoRequest.completed() != null) {
            todo.setCompleted(todoRequest.completed());
        }

        return toResponse(repo.save(todo));
    }

    /**
     * Convert a Todo entity to a TodoResponse DTO.
     * Helper method to transform a Todo entity into a TodoResponse DTO.
     *
     * @param todo The Todo entity to convert.
     * @return A TodoResponse DTO representing the Todo entity.
     */
    private TodoResponse toResponse(Todo todo) {
        // Handle nullable fields
        String dueDateStr = (todo.getDueDate() != null) ? todo.getDueDate().toString() : null;
        String priorityStr = (todo.getPriority() != null) ? todo.getPriority().name().toLowerCase() : null;

        // Convert Instant to LocalDateTime
        LocalDateTime createdAtLDT = LocalDateTime.ofInstant(todo.getCreatedAt(), ZoneId.systemDefault());
        LocalDateTime updateAtLDT = LocalDateTime.ofInstant(todo.getUpdatedAt(), ZoneId.systemDefault());

        // Create formatter
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Format to String
        String createdAtStr = createdAtLDT.format(formatter);
        String updatedAtStr = updateAtLDT.format(formatter);

        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.isCompleted(),
                dueDateStr,
                priorityStr,
                createdAtStr,
                updatedAtStr
        );
    }

    /**
     * Toggle the `completed` status of a Todo item by its ID.
     *
     * @param id The ID of the Todo item to toggle.
     * @return A TodoResponse object representing the updated Todo item.
     * @throws BusinessException if the Todo item with the given ID is not found.
     */
    @Transactional
    public TodoResponse toggleCompleted(Long id) {
        Todo todo = repo.findById(id)
                .orElseThrow(() -> new BusinessException("Todo id: " + id + " is not found"));

        // Flip the `completed` status
        todo.setCompleted(!todo.isCompleted());

        return toResponse(repo.save(todo));
    }

    // -------------------- READ --------------------
    /**
     * Retrieve a single Todo item by its ID.
     *
     * @param id The ID of the Todo item to retrieve.
     * @return A TodoResponse object representing the retrieved Todo item.
     * @throws BusinessException if the Todo item with the given ID is not found.
     */
    public TodoResponse getOne(Long id) {
        return toResponse(
                repo.findById(id)
                        .orElseThrow(() -> new BusinessException("Todo id: " + id + " is not found"))
        );
    }

    /**
     * Retrieve all Todo items, sorted by due date in ascending order.
     *
     * @return A list of TodoResponse objects representing all Todo items.
     */
    public List<TodoResponse> getAll() {
        return repo.findAllByOrderByDueDateAsc().
                stream().
                map(this::toResponse).
                toList();
    }


    // -------------------- DELETE ---------------------
    /**
     * Delete a Todo item by its ID.
     *
     * @param id The ID of the Todo item to delete.
     * @throws BusinessException if the Todo item with the given ID is not found.
     */
    @Transactional
    public void deleteById(Long id) {
        if (!repo.existsById(id)) {
            throw new BusinessException("Todo id: " + id + " is not found");
        }
        repo.deleteById(id);
    }



}
