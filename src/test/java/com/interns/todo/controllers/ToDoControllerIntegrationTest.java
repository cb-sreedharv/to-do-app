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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ToDoControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ToDoRepository toDoRepository;

  @Test
  public void createTaskTest() throws Exception {
    Task task = new Task();
    task.setTaskDesc("Test Task");
    Gson gson = new Gson();

    mockMvc.perform(post("/todo")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(task)))
        .andExpect(status().isOk());

    Optional<Task> task1 = toDoRepository.findById(1L);
    assertThat(task1.get().getTaskDesc()).isEqualTo("Test Task");
  }


}
