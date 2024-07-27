package com.taskmanagment.digi.repository;

import com.taskmanagment.digi.entities.Task;
import com.taskmanagment.digi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TaskRepository extends JpaRepository<Task,Integer> {

    /**
     * customized method to find the task record associated with specific ID
     * @param taskId ID of the required task
     * @return Task object associated the ID
     */
    Task findByTaskId(Long taskId);


}