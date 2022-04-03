package com.interns.todo.controllers;

import com.google.gson.Gson;
import com.interns.todo.model.Task;
import com.interns.todo.repository.ToDoRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ToDoControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ToDoRepository toDoRepository;

  @Test
  public void testA_createTaskTestSuccessfully() throws Exception {
    Task task = new Task();
    task.setTaskDesc("Test Task");
    Gson gson = new Gson();

    mockMvc.perform(post("/todo")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(task)))
        .andDo(print())
        .andExpect(status().isOk());

    Optional<Task> task1 = toDoRepository.findById(1L);
    assertThat(task1.isPresent()).isTrue();
    task1.ifPresent(value -> assertThat(value.getTaskDesc()).isEqualTo("Test Task"));
  }

  @Test
  public void testB_CreateTaskTestUnSuccessfully() throws Exception {
    Task task = new Task();
    String s = String.format("%0" + 300 + "d", 0).replace('0', 't');
    task.setTaskDesc(s);
    Gson gson = new Gson();

    mockMvc.perform(post("/todo")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(task)))
        .andDo(print())
        .andExpect(status().isBadRequest());

    Iterable<Task> task1 = toDoRepository.findAll();
    int count = 0;
    for (Task t : task1) {
      count++;
    }
    assertThat(count).isEqualTo(1);
  }

  @Test
  public void testC_updateTaskTestSuccessfully() throws Exception {
    Task task = new Task();
    task.setTaskDesc("Modified Task");
    Gson gson = new Gson();

    mockMvc.perform(put("/todo/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(task)))
        .andDo(print())
        .andExpect(status().isOk());

    Optional<Task> task1 = toDoRepository.findById(1L);
    assertThat(task1.isPresent()).isTrue();
    task1.ifPresent(value -> assertThat(value.getTaskDesc()).isEqualTo("Modified Task"));
  }

  @Test
  public void testD_UpdateTaskTestUnSuccessfully() throws Exception {
    Task task = new Task();
    String s = String.format("%0" + 300 + "d", 0).replace('0', 't');
    task.setTaskDesc(s);
    Gson gson = new Gson();

    mockMvc.perform(put("/todo/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(task)))
        .andDo(print())
        .andExpect(status().isBadRequest());

    Optional<Task> task1 = toDoRepository.findById(1L);
    assertThat(task1.isPresent()).isTrue();
    task1.ifPresent(value -> assertThat(value.getTaskDesc()).isEqualTo("Modified Task"));
  }

  @Test
  public void testE_getTaskTestSuccessfully() throws Exception {
    mockMvc.perform(get("/todo"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].taskDesc", is("Modified Task")));
  }

  @Test
  public void testF_deleteTaskTestUnSuccessfully() throws Exception {
    mockMvc.perform(delete("/todo/2"))
        .andDo(print())
        .andExpect(status().isBadRequest());

    Optional<Task> task1 = toDoRepository.findById(1L);
    assertThat(task1.isPresent()).isTrue();
  }

  @Test
  public void testG_deleteTaskTestSuccessfully() throws Exception {
    mockMvc.perform(delete("/todo/1"))
        .andDo(print())
        .andExpect(status().isOk());

    Optional<Task> task1 = toDoRepository.findById(1L);
    assertThat(task1.isPresent()).isFalse();
  }
}
