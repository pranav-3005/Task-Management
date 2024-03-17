package com.example.taskManagement;

import com.example.taskManagement.model.User;
import com.example.taskManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class TaskManagementApplication {

	public static void main(String[] args) {


		SpringApplication.run(TaskManagementApplication.class, args);
	}

}
