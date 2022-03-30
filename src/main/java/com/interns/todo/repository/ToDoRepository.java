package com.interns.todo.repository;

import com.interns.todo.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for accessing and modifying tasks in ToDo List.
 */
@Repository
public interface ToDoRepository extends CrudRepository<Task, Long> {

}
