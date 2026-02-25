package org.example.backend.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TodoPatchResponse {
    private long id;
    private String title;
    private String description;
}
