package com.taskmanagment.digi.dto;

import com.taskmanagment.digi.entities.TaskPriority;
import com.taskmanagment.digi.entities.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor

public class TaskFilterationDto {
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate dueDate;
}
