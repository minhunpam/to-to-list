package org.example.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.dtos.TodoDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "todos")
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;

    public TodoEntity(TodoDto todoDto) {
        this.id = null;
        this.title = todoDto.getTitle();
        this.description = todoDto.getDescription();
    }
}
