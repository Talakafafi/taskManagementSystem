package com.taskmanagment.digi.repository;


import com.taskmanagment.digi.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * customized method to find the project record associated with specific ID
     * @param projectId ID of the required user
     * @return project object associated the ID
     */
    Project findByProjectId(Long projectId);
}

