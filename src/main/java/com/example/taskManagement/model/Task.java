package com.example.taskManagement.model;

import com.example.taskManagement.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String title;

    String description;

    String dueDate;

    @Enumerated(value = EnumType.STRING)
    Status status;

    int userId;


}
