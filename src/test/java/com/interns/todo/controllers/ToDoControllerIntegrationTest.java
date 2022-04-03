package com.interns.todo.controllers;

import com.google.gson.Gson;
import com.interns.todo.model.Task;
import com.interns.todo.repository.ToDoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ToDoControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ToDoRepository toDoRepository;

  @Test
  public void createTaskTestSuccessfully() throws Exception {
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
  public void createTaskTestUnSuccessfully() throws Exception {
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
    for (Task t : task1) {
      System.out.println(t.getTaskId());
      System.out.println(t.getTaskDesc());
    }
  }


}
