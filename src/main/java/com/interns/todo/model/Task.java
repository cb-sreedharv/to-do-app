package com.interns.todo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Model Class for ToDoItem.
 */
@Entity
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long taskId;
  private String taskDesc;

  public long getToDoId() {
    return this.taskId;
  }

  public void setToDoId(long toDoId) {
    this.taskId = toDoId;
  }

  public String getTaskDesc() {
    return this.taskDesc;
  }

  public void setTaskDesc(String taskDesc) {
    this.taskDesc = taskDesc;
  }



}
