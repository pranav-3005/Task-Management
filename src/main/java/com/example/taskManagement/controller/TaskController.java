package com.example.taskManagement.controller;

import com.example.taskManagement.DTO.requestDTO.TaskRequest;
import com.example.taskManagement.Service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    //Contains APIs related to tasks

    @Autowired
    Service service;

    //------------------------------------[POST]
    @PostMapping("/addTask")
    public ResponseEntity addTask(@RequestBody TaskRequest taskRequest)
    {
        return service.addTask(taskRequest);
    }


    //----------------------------------[GET]

    @GetMapping("/getTask")
    public ResponseEntity getTask(@RequestParam int taskId)
    {
        return service.getTask(taskId);
    }

    @GetMapping("/getAllTasks")
    public ResponseEntity getAllTasks()
    {
        return service.getAllTasks();
    }

    @GetMapping("/getTasksByStatus")
    public ResponseEntity getTasksByStatus(@RequestParam String status)
    {
        return service.getTasksByStatus(status);
    }

    @GetMapping("/getTasksByTitleAscendingOrder")
    public ResponseEntity getTasksByTitleAscending()
    {
        return service.getTasksByTitleAscending();
    }

    @GetMapping("/getTasksByTitleDescendingOrder")
    public ResponseEntity getTasksByTitleDescending()
    {
        return service.getTasksByTitleDescending();
    }


    //------------------------------------[PUT]

    @PutMapping("/updateTaskStatus")
    public ResponseEntity updateTaskStatus(@RequestParam int taskId,@RequestBody String newStatus)
    {
        return service.updateTaskStatus(taskId,newStatus);
    }

    @PutMapping("/updateDescription")
    public ResponseEntity updateDescription(@RequestParam int taskId,@RequestBody String description)
    {
        return service.updateDescription(taskId,description);
    }

    @PutMapping("/updateDueDate")
    public ResponseEntity updateDueDate(@RequestParam int taskId,@RequestBody String newDate)
    {
        return service.updateDueDate(taskId,newDate);
    }

    @PutMapping("/updateTitle")
    public ResponseEntity updateTitle(@RequestParam int taskId, @RequestBody String newTitle)
    {
        return service.updateTitle(taskId,newTitle);
    }

    //--------------------------------------------[DELETE]

    @DeleteMapping("/deleteTask")
    public ResponseEntity deleteTask(@RequestParam int taskId)
    {
        return service.deleteTask(taskId);
    }

    //--------------------------------------------------

}
