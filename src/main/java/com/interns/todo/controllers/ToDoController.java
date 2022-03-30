package com.interns.todo.controllers;

import com.interns.todo.model.Task;
import com.interns.todo.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public String printString() {
    return "ToDoList";
  }

  @GetMapping("/todo")
  public Iterable<Task> getList() {
    return toDoService.getTasks();
  }

  @PostMapping("/todo")
  public ResponseEntity createToDo(@RequestBody Task task) {
    toDoService.createTask(task);
    return new ResponseEntity(HttpStatus.OK);
  }
}
