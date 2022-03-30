package com.interns.todo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * ToDoModel.
 */
@Entity
public class ToDo {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long toDoId;
  private String taskDesc;

  public long getToDoId() {
    return this.toDoId;
  }

  public void setToDoId(long toDoId) {
    this.toDoId = toDoId;
  }

  public String getTaskDesc() {
    return this.taskDesc;
  }

  public void setTaskDesc(String taskDesc) {
    this.taskDesc = taskDesc;
  }



}
