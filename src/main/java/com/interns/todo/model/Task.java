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

  public long getTaskId() {
    return this.taskId;
  }

  public void setTaskId(long taskId) {
    this.taskId = taskId;
  }

  public String getTaskDesc() {
    return this.taskDesc;
  }

  public void setTaskDesc(String taskDesc) {
    this.taskDesc = taskDesc;
  }



}
