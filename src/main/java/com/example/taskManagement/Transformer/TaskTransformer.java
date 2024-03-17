package com.example.taskManagement.Transformer;

import com.example.taskManagement.DTO.requestDTO.TaskRequest;
import com.example.taskManagement.DTO.responseDTO.TaskResponse;
import com.example.taskManagement.model.Task;

public class TaskTransformer {

    public static Task taskRequestToTask(TaskRequest taskRequest, int userId)
    {
        return Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .dueDate(taskRequest.getDueDate())
                .status(taskRequest.getStatus())
                .userId(userId)
                .build();
    }

    public static TaskResponse taskToTaskResponse(Task task)
    {
        return TaskResponse.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .build();
    }
}
