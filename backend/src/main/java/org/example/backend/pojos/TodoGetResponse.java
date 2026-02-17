package org.example.backend.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TodoGetResponse {
    long id;
    String title;
    String description;
}
