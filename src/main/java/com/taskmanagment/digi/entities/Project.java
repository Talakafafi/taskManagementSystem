package com.taskmanagment.digi.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Table(name = "PROJECTS")
public class Project {

    @Id
    @GeneratedValue(generator = "projectIdGen", strategy = GenerationType.SEQUENCE)
    @Column(name = "PROJECT_ID")
    private Long projectId;

    @Column(name = "PROJECT_NAME")
    private String name;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL , orphanRemoval = true) //when a Project is deleted, all associated Task entities are also deleted.
    private List<Task> tasks;


}
