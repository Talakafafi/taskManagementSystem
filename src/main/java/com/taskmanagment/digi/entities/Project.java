package com.taskmanagment.digi.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Table(name = "PROJECTS")
public class Project {

    @Id
    @GeneratedValue(generator = "projectIdGen", strategy = GenerationType.AUTO)
    @Column(name = "PROJECT_ID")
    private Long projectId;

    @Column(name = "PROJECT_NAME")
    private String name;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;


}
