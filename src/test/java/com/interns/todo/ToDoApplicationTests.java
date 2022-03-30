package com.interns.todo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.interns.todo.controllers.ToDoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
class ToDoApplicationTests {

  @Autowired
  private ToDoController controller;

  @Test
  void contextLoads() {
    assertThat(controller).isNotNull();
  }

}
