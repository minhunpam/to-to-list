package com.example.to_do_list.DTO;

import java.time.Instant;
import java.time.LocalDate;

public record TodoResponse(
   Long id,
   String title,
   boolean completed,
   String dueDate,
   String priority,
   String createdAt,
   String updatedAt
) {}
