package com.taskmanagment.digi.dto;

import com.taskmanagment.digi.entities.TaskPriority;
import com.taskmanagment.digi.entities.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class TaskUserRequestDto {
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private TaskStatus status;
    @NotNull
    private TaskPriority priority;
    @NotNull
    private LocalDate dueDate;

    private Set<Long> usersId;
}
/**
 {
 "title": "Sample Task Title",
 "description": "This is a sample task description.",
 "status": "INCOMPLETE",
 "priority": "HIGH",
 "dueDate": "2024-07-31",
 "usersId": [1, 2, 3]
 }
 */