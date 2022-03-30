package com.interns.todo.service;

import com.interns.todo.exceptions.TaskNotFoundException;
import com.interns.todo.model.Task;
import com.interns.todo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * ToDoService.
 */
@Service
public class ToDoService {

  @Autowired
  ToDoRepository toDoRepository;

  public void createTask(Task task) throws DataIntegrityViolationException, Exception {
    toDoRepository.save(task);
  }

  public Iterable<Task> getTasks() {
    return toDoRepository.findAll();
  }

  /**
   * Method to update task.
   *
   * @param id - task object containing task id and new task description.
   * @param taskDesc - new task description.
   * @throws TaskNotFoundException - Task with mentioned id does not exist in database.
   * @throws Exception - for Internal Server Errors.
   */
  public void updateTask(long id, String taskDesc) throws TaskNotFoundException, DataIntegrityViolationException, Exception {
    if (!toDoRepository.findById(id).isPresent()) {
      throw new TaskNotFoundException("Task does not exist");
    }
    Task task = new Task();
    task.setTaskId(id);
    task.setTaskDesc(taskDesc);
    toDoRepository.save(task);
  }

  /**
   * Method to update task.
   *
   * @param id - id of the task to be deleted.
   * @throws TaskNotFoundException - Task with mentioned id does not exist in database.
   * @throws Exception - for Internal Server Errors.
   */
  public void deleteTask(long id) throws TaskNotFoundException, Exception {
    if (!toDoRepository.findById(id).isPresent()) {
      throw new TaskNotFoundException("Task does not exist");
    }
    toDoRepository.deleteById(id);
  }

}
