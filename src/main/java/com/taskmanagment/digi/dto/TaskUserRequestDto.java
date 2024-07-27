package com.taskmanagment.digi.dto;
/**
 used as the body of tasks/addTask API
 {
 "title": "Sample Task Title",
 "description": "This is a sample task description.",
 "status": "INCOMPLETE",
 "priority": "HIGH",
 "dueDate": "2024-07-31",
 "usersId": [1, 2, 3]
 }
 **/
import com.taskmanagment.digi.entities.TaskPriority;
import com.taskmanagment.digi.entities.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class TaskUserRequestDto {
    @NotNull(message = "Title cannot be null")
    private String title;
    @NotNull(message = "Description cannot be null")
    private String description;
    @NotNull(message = "Status cannot be null")
    private TaskStatus status;
    @NotNull(message = "Priority cannot be null")
    private TaskPriority priority;
    @NotNull(message = "Date cannot be null ")
    private LocalDate dueDate;

    private List<Long> usersId;
}
