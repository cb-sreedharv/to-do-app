package com.interns.todo.service;

import com.interns.todo.model.Task;
import com.interns.todo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ToDoService.
 */
@Service
public class ToDoService {

  @Autowired
  ToDoRepository toDoRepository;

  public void createTask(Task task) {
    toDoRepository.save(task);
  }

  public Iterable<Task> getTasks() {
    return toDoRepository.findAll();
  }

}
