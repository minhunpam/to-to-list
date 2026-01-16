package com.example.to_do_list.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record TodoRequest(
    @NotBlank @Size(max = 100) String title,
    LocalDate dueDate,
    String priority,
    Boolean completed
) {}