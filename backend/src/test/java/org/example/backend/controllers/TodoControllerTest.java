package org.example.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.dtos.TodoDto;
import org.example.backend.pojos.TodoPostResponse;
import org.example.backend.services.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TodoService todoService;


    private static class RandomStringGenerator {
        private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
        private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
        private static final String digits = "0123456789"; // 0-9
        private static final String specials = "~=+%^*/()[]{}/!@#$?|";

        private static final String ALL = alpha + alphaUpperCase + digits + specials;
        private static final Random generator = new Random();

        public static String generateRandomString(int numberOfCharacters) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < numberOfCharacters; ++i) {
                int randomNumber = generator.nextInt(ALL.length());
                char randomCharacter = ALL.charAt(randomNumber);
                stringBuilder.append(randomCharacter);
            }

            return stringBuilder.toString();
        }
    }

    @Test
    void createTodo_returnsCreated_whenPayloadIsValid() throws Exception {
        TodoDto request = new TodoDto("Buy milk", "From the nearby store");
        TodoPostResponse response = new TodoPostResponse("Buy milk", "From the nearby store");

        when(todoService.createTodo(any(TodoDto.class))).thenReturn(response);

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Buy milk"))
                .andExpect(jsonPath("$.description").value("From the nearby store"));

        verify(todoService).createTodo(any(TodoDto.class));
    }

    @Test
    void createTodo_returnsBadRequest_whenTitleIsBlank() throws Exception {
        TodoDto invalidRequest = new TodoDto("   ", "Valid description");

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("\"field\":\"title\"")));

        verifyNoInteractions(todoService);
    }

    @Test
    void createTodo_returnsBadRequest_whenDescriptionLengthIsMoreThan250Characters() throws Exception {
        String randomString = RandomStringGenerator.generateRandomString(300);
        TodoDto invalidRequest = new TodoDto("valid title", randomString);

        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("\"field\":\"description\"")));

        verifyNoInteractions(todoService);
    }

}
