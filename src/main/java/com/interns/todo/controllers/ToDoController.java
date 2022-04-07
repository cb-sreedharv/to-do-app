package com.interns.todo.controllers;

import com.interns.todo.exceptions.TaskNotFoundException;
import com.interns.todo.model.Task;
import com.interns.todo.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ToDoController.
 */
@RestController
public class ToDoController {

  @Autowired
  ToDoService toDoService;

  @GetMapping("/")
  public String printString() {
    return "ToDoList Application";
  }

  @GetMapping("/todo")
  public Iterable<Task> getList() {
    return toDoService.getTasks();
  }

  /**
   * Method to add a Task to ToDo List.
   *
   * @param task - Task to be added to the ToDo List.
   */
  @PostMapping("/todo")
  public ResponseEntity createTask(@RequestBody Task task) {
    try {
      toDoService.createTask(task);
      return new ResponseEntity(HttpStatus.OK);
    } catch (DataIntegrityViolationException e) {
      return new ResponseEntity(e.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Method to update a Task.
   *
   * @param id - task object containing task id and new task description.
   * @param task - new task description.
   */
  @PutMapping("/todo/{taskId}")
  public ResponseEntity updateTask(@PathVariable("taskId") long id, @RequestBody Task task) {
    try {
      toDoService.updateTask(id, task);
      return new ResponseEntity(HttpStatus.OK);
    } catch (TaskNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (DataIntegrityViolationException e) {
      return new ResponseEntity(e.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Method to update a Task.
   *
   * @param id - id of the task to be deleted.
   */
  @DeleteMapping("/todo/{taskId}")
  public ResponseEntity deleteTask(@PathVariable("taskId") long id) {
    try {
      toDoService.deleteTask(id);
      return new ResponseEntity(HttpStatus.OK);
    } catch (TaskNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "**")
  public ResponseEntity handleOtherRoutes() {
    return new ResponseEntity(HttpStatus.NOT_FOUND);
  }
}
