package com.example.to_do_list.controller;

import org.springframework.web.bind.*;
import com.example.to_do_list.model.ToDo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class ToDoController {
    private List<ToDo> toDoList = new ArrayList<>();

    @GetMapping
    public List<ToDo> getAllToDo() {
        return toDoList;
    }

    @PostMapping
    public ToDo addToDo(@RequestBody ToDo todo) {
        todo.setId((long) (toDoList.size() + 1));
        toDoList.add(todo);
        return todo;
    }
}
