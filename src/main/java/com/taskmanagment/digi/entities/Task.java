package com.taskmanagment.digi.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Table(name = "TASKS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "taskId")
public class Task {
    @Id
    @GeneratedValue(generator = "taskIdGen",strategy = GenerationType.AUTO )
    @Column(name = "TASK_ID")
    private Long taskId;

    @Column(name = "TASK_TITLE")
    private String title;

    @Column(name = "TASK_DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "TASK_STATUS")
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "TASK_PRIORITY")
    private TaskPriority priority;


    @Column(name = "TASK_DUE_DATE")
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "REF_PROJECT_ID" )
    private Project project;


    @ManyToMany//lazy fetch by default
        @JoinTable(name = "TASKS_USERS", joinColumns = {@JoinColumn(name = "REF_TASK_ID", referencedColumnName = "TASK_ID" )
    }, inverseJoinColumns = {@JoinColumn(name = "REF_USER_ID", referencedColumnName = "USER_ID" )} )
    private Set<User> users ;


    public Task(String taskTitle, String taskDescription, TaskStatus taskStatus, TaskPriority taskPriority, LocalDate date) {
        this.setTitle(taskTitle);
        this.setDescription(taskDescription);
        this.setStatus(taskStatus);
        this.setPriority(taskPriority);
        this.setDueDate(date);
    }


}
