package com.interns.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ToDoApplication {
    public static void main(final String[] args) {
        SpringApplication.run(ToDoApplication.class, args);
    }
}
