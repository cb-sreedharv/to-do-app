package com.interns.todo.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.interns.todo.model.Task;
import com.interns.todo.service.ToDoService;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Class containing unit tests for ToDoController.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ToDoController.class)
public class ToDoControllerUnitTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ToDoService toDoService;

  @Test
  public void shouldReturnDefaultMessage() throws Exception {
    this.mockMvc
        .perform(get("/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("ToDoList")));
  }

  @Test
  public void getAllTasksSuccessfully() throws Exception {
    Task task1 = new Task();
    task1.setTaskId(1);
    task1.setTaskDesc("First Task");

    Task task2 = new Task();
    task2.setTaskId(2);
    task2.setTaskDesc("Second Task");

    List<Task> allTasks = Arrays.asList(task1, task2);

    when(toDoService.getTasks())
        .thenReturn(allTasks);

    this.mockMvc.perform(get("/todo")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].taskDesc", is(task1.getTaskDesc())));
  }

  @Test
  public void createTasksSuccessfully() throws Exception {
    Task task1 = new Task();
    task1.setTaskDesc("First Task");
    Gson gson = new Gson();

    this.mockMvc.perform(post("/todo")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(task1)))
        .andDo(print())
        .andExpect(status().isOk());

    verify(toDoService).createTask(Mockito.any(Task.class));
  }

  @Test
  public void createTasksUnSuccessfully_DataIntegrityViolationException() throws Exception {
    Task task1 = new Task();
    task1.setTaskDesc("First Task");
    Gson gson = new Gson();

    doThrow(DataIntegrityViolationException.class)
        .when(toDoService)
        .createTask(Mockito.any(Task.class));

    this.mockMvc.perform(post("/todo")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(task1)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void createTasksUnSuccessfully_Exception() throws Exception {
    Task task1 = new Task();
    task1.setTaskDesc("First Task");
    Gson gson = new Gson();

    doThrow(Exception.class)
        .when(toDoService)
        .createTask(Mockito.any(Task.class));

    this.mockMvc.perform(post("/todo")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(task1)))
        .andDo(print())
        .andExpect(status().isInternalServerError());

  }

  @Test
  public void updateTasksSuccessfully() throws Exception {
    Task task1 = new Task();
    task1.setTaskDesc("Modified Task");
    Gson gson = new Gson();

    this.mockMvc.perform(put("/todo/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(task1)))
        .andDo(print())
        .andExpect(status().isOk());
    verify(toDoService).updateTask(Mockito.any(Long.class), Mockito.any(Task.class));

  }

  @Test
  public void updateTasksUnSuccessfully_DataIntegrityViolationException() throws Exception {
    Task task1 = new Task();
    task1.setTaskDesc("Modified Task");
    Gson gson = new Gson();

    doThrow(DataIntegrityViolationException.class)
        .when(toDoService)
        .updateTask(Mockito.any(Long.class), Mockito.any(Task.class));

    this.mockMvc.perform(put("/todo/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(task1)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateTasksUnSuccessfully_Exception() throws Exception {
    Task task1 = new Task();
    task1.setTaskDesc("Modified Task");
    Gson gson = new Gson();

    doThrow(Exception.class)
        .when(toDoService)
        .updateTask(Mockito.any(Long.class), Mockito.any(Task.class));

    this.mockMvc.perform(put("/todo/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(task1)))
        .andDo(print())
        .andExpect(status().isInternalServerError());
  }

  @Test
  public void deleteTaskSuccessfully() throws Exception {
    this.mockMvc.perform(delete("/todo/1"))
        .andDo(print())
        .andExpect(status().isOk());
    verify(toDoService).deleteTask(1);
  }

  @Test
  public void sendNotFoundError() throws Exception {
    this.mockMvc.perform(get("/random"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

}
