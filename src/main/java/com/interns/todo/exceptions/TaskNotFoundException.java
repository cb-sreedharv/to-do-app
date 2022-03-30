package com.interns.todo.exceptions;

/**
 * Exception class when Task does not exist in Database.
 */
public class TaskNotFoundException extends RuntimeException {
  private String message;

  public TaskNotFoundException(String message) {
    super(message);
    this.message = message;
  }

  public TaskNotFoundException() {

  }
}
