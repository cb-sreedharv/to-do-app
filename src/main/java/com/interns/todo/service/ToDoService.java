package com.interns.todo.service;

import com.interns.todo.model.ToDo;
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

  public void saveToDo(ToDo toDo) {
    toDoRepository.save(toDo);
  }

  public Iterable<ToDo> getAllToDo() {
    return toDoRepository.findAll();
  }

}
