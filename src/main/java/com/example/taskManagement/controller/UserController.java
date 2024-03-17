package com.example.taskManagement.controller;

import com.example.taskManagement.Service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    Service service;

    @PostMapping("/signup")
    public ResponseEntity addUser(@RequestParam String userName, @RequestParam String password)
    {
        return service.addUser(userName,password);
    }

    @PostMapping("/enableAdmin")
    public ResponseEntity enableAdmin()
    {
        return service.enableAdmin();
    }
}
