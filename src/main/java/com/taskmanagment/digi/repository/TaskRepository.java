package com.taskmanagment.digi.repository;

import com.taskmanagment.digi.entities.Task;
import com.taskmanagment.digi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TaskRepository extends JpaRepository<Task,Integer> {

    Task findByTaskId(Long taskId);
}