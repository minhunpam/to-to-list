package com.example.to_do_list;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.example.to_do_list.domain.Todo;
import com.example.to_do_list.service.TodoService;
import com.example.to_do_list.service.BusinessException;


@SpringBootApplication
public class ToDoListApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDoListApplication.class, args);
    }

    @Bean
    CommandLineRunner demoData(TodoService service) {
        return args -> {
          try {
              var bad = new Todo();
              bad.setTitle("");
              service.save(bad);
          } catch (BusinessException e) {
              System.out.println(e.getMessage());
          }

          var good = new Todo();
          good.setTitle("First todo");
          var saved = service.save(good);

          saved = service.toggleCompleted(saved.getId());
          var all = service.findAllByOrderByDueDateAsc();

          System.out.println("Total todos after seeding: " + all.size());
            System.out.println("First todo: id=" + saved.getId() +
                    ", completed=" + service.findById(saved.getId()).isCompleted());
        };
    }

}
