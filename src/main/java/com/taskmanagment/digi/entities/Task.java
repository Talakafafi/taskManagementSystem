package com.taskmanagment.digi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Data
@Entity
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Table(name = "TASKS")
public class Task {
    @Id
    @GeneratedValue(generator = "taskIdGen",strategy = GenerationType.AUTO )
    @Column(name = "TASK_ID")
    private int taskId;

    @Column(name = "TASK_TITLE")
    private String title;

    @Column(name = "TASK_DESCRIPTION")
    private String description;

    @Column(name = "TASK_STATUS")
    private TaskStatus status;

    @Column(name = "TASK_PRIORITY")
    private TaskPriority priority;

    @Column(name = "TASK_DUE_DATE")
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "TASK_PROJECT")
    private Project project;


}
