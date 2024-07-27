package com.taskmanagment.digi.dto;
/**
 used as the body of tasks/updateTask API
 {
 "title": "Sample Task Title",
 "description": "This is a sample task description.",
 "status": "INCOMPLETE",
 "priority": "HIGH",
 "dueDate": "2024-07-31",
 "usersId": [1, 2, 3]
 }
 */
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
public class TaskUpdateRequestDto {
        private String title;

        private String description;

        private TaskStatus status;

        private TaskPriority priority;

        private LocalDate dueDate;

    }


