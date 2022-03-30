package com.interns.todo.repository;

import com.interns.todo.model.ToDo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * ToDoRepository.
 */
@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long> {

}
