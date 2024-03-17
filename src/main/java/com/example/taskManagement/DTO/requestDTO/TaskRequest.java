package com.example.taskManagement.DTO.requestDTO;

import com.example.taskManagement.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TaskRequest {

    String title;

    String description;

    String dueDate;

    Status status;
}
