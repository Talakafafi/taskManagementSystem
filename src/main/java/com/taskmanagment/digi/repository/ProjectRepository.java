package com.taskmanagment.digi.repository;


import com.taskmanagment.digi.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProjectRepository extends JpaRepository<Project, Long> {


    Project findByProjectId(Long projectId);
}

