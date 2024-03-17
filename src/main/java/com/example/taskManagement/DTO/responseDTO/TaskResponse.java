package com.example.taskManagement.DTO.responseDTO;

import com.example.taskManagement.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TaskResponse {

    String title;

    String description;

    String dueDate;

    Status status;
}
