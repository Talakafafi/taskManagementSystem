package com.taskmanagment.digi.dto;
/**
 used as the body of tasks/addTask API

 {
 "title": "Task Title",
 "description": "Task description goes here.",
 "status": "COMPLETED",
 "priority": "HIGH",
 "dueDate": "2024-07-31",
 "users": [
 {
 "id": 1,
 "name": "User1",
 "email": "user1@gmail.com"
 },
 }

 */
import com.taskmanagment.digi.entities.TaskPriority;
import com.taskmanagment.digi.entities.TaskStatus;
import com.taskmanagment.digi.entities.User;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class TaskRequestDto {
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

    private Set<User> users;
}
