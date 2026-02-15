package org.example.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    @NotBlank(message = "[FAILED] to-do title must not be blank")
    @Size(min = 1, max = 100, message = "[FAILED] to-do title's length must be between 1 and 100 characters")
    private String title;

    @NotBlank (message = "[FAILED] to-do description must not be blank")
    @Size (min = 1, max = 250, message = "[FAILED] to-do description's length must be between 1 and 250 characters")
    private String description;
}
