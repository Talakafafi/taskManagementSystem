package com.taskmanagment.digi.service;
/**
 * Step 4
 * Where all the business logic is implemented.
 * The service layer sits between the controller and the repository layers, handling the
 * business rules and computations necessary to fulfill a request before passing data to the repository
 * for database operations or to the controller for sending  a response.
 * when DTO works as a shell for entity class (ensuring data transfer efficiently without knowing the implementation details)
 * and as a template for specified the parameters passed in methods ,
 *
 */

import com.taskmanagment.digi.dto.UserRequestDto;
import com.taskmanagment.digi.entities.Task;
import com.taskmanagment.digi.entities.User;
import com.taskmanagment.digi.exception.type.IdNotFoundException;
import com.taskmanagment.digi.repository.TaskRepository;
import com.taskmanagment.digi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TaskRepository taskRepository;

    /**
     * saves the user in the database
     * @param userRequest
     * @return the Inserted User
     */
    public User addUser( UserRequestDto userRequest){
        return userRepository.save(new User(userRequest.getUsername(), userRequest.getEmail()));
    }

    /**
     * get all users
     * @return
     */
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    /**
     * get specific user
     * @param userId
     * @return
     * @throws IdNotFoundException
     */

    public User getUser(Long userId) throws IdNotFoundException {
        User user=   userRepository.findByUserId(userId);
        if(user!=null){
          return user;
        }else{
            throw new IdNotFoundException(User.class.getName(), userId);
        }
    }

    /**
     * updats the user
     * @param userId
     * @param newUser
     * @return
     * @throws IdNotFoundException
     */
    public User updateUser(Long userId, UserRequestDto newUser) throws IdNotFoundException {
        User updatedUser = userRepository.findByUserId(userId);
        if(updatedUser!=null){
        updatedUser.setEmail(newUser.getEmail() == null ? updatedUser.getEmail() : newUser.getEmail());
        updatedUser.setUsername(newUser.getUsername() == null ? updatedUser.getUsername() : newUser.getUsername());
            return updatedUser;
        }else{
            throw new IdNotFoundException(User.class.getName(), userId);
    }
    }

    public User removeUser(Long userId) throws IdNotFoundException {
        User deletedUser = userRepository.findByUserId(userId);
        if (deletedUser != null) {

          Set<Task> tasks = deletedUser.getTasks();


            userRepository.delete(deletedUser);
            return deletedUser;
        } else {
            throw new IdNotFoundException(User.class.getName(), userId);
        }
    }
    public Set<Task> getUserTasks(Long userId) throws IdNotFoundException {
        User user=   userRepository.findByUserId(userId);
        if(user!=null){
            return user.getTasks();
        }else{
            throw new IdNotFoundException(User.class.getName(), userId);
        }
    }

}
