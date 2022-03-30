package com.interns.todo.controllers;

import com.interns.todo.model.ToDo;
import com.interns.todo.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *ToDoController.
 */
@RestController
public class ToDoController {

  @Autowired
  ToDoService toDoService;

  @GetMapping("/")
  public String getToDo() {
    return "ToDoController";
  }

  @GetMapping("/todo")
  public Iterable<ToDo> viewAllToDo() {
    return toDoService.getAllToDo();
  }

  @PostMapping("/todo")
  public Long createToDo(@RequestBody ToDo toDo) {
    toDoService.saveToDo(toDo);
    return toDo.getToDoId();
  }
}
