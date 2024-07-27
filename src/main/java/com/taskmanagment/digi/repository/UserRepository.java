package com.taskmanagment.digi.repository;
/**
 * Step 3
 * To be able to leverage methods from JpaRepository and CrudRepository
 * save() , find() , remove and other methods to manipulate the records in the database
 * it has an essential role at the service layer
 */

import com.taskmanagment.digi.entities.Task;
import com.taskmanagment.digi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Set;

@RepositoryRestResource

/**
 * handles database operations like fetching, deleting, and inserting records in the USER table
 */
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * customized method to find the user record associated with specific ID
     * @param userId ID of the required user
     * @return User object associated the ID
     */
    User findByUserId(Long userId);



}
