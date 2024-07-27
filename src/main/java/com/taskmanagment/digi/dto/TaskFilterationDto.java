package com.taskmanagment.digi.dto;
/**

 used as the body of task /filter API

 {
 "status": "INPROGRESS",
 "priority": "HIGH",
 "dueDate": "2024-08-15"
 }
 */
import com.taskmanagment.digi.entities.TaskPriority;
import com.taskmanagment.digi.entities.TaskStatus;
import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor

public class TaskFilterationDto {
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate dueDate;
}

