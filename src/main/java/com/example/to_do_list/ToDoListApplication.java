package com.example.to_do_list;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.example.to_do_list.domain.Todo;
import com.example.to_do_list.repository.TodoRepository;


@SpringBootApplication
public class ToDoListApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDoListApplication.class, args);
    }

    @Bean
    CommandLineRunner demoData(TodoRepository repo) {
        return args -> {
          if (repo.count() == 0) {
              var todo = new Todo();
              todo.setTitle("Learn Spring Boot");
              repo.save(todo);
          }
          System.out.println("Total todos in DB: " + repo.count());
        };
    }

}
