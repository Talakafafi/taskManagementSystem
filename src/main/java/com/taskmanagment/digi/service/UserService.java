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
import com.taskmanagment.digi.entities.User;
import com.taskmanagment.digi.exception.type.UserNotFoundException;
import com.taskmanagment.digi.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired // handles object creation for you
    private UserRepository userRepository;

    /**
     * saves the user in the database
     * @param userRequest
     * @return the Inserted User
     */
    public User addUser( UserRequestDto userRequest){
        return  userRepository.save(User.build(0,userRequest.getUsername(),userRequest.getEmail()));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    /**
     * gets the user with specific ID
     * @param userId
     * @return
     * @throws UserNotFoundException customized user thrown when the inserted ID does not exist
     */
    public User getUser(int userId) throws UserNotFoundException {
        User user=   userRepository.findByUserId(userId);
        if(user!=null){
          return user;
        }else{
            throw new UserNotFoundException("User not found with id : "+userId);
        }
    }

    public User updateUser(int userId , UserRequestDto newUser ) throws UserNotFoundException {
        User updatedUser = userRepository.findByUserId(userId);
        if(updatedUser!=null){
        updatedUser.setEmail(newUser.getEmail() == null ? updatedUser.getEmail() : newUser.getEmail());
        updatedUser.setUsername(newUser.getUsername() == null ? updatedUser.getUsername() : newUser.getUsername());
            return updatedUser;
        }else{
            throw new UserNotFoundException("User not found with id : "+userId);
    }
    }

    public User removeUser(int userId) throws UserNotFoundException {
        User deletedUser = userRepository.findByUserId(userId);
        if (deletedUser != null) {
            userRepository.delete(deletedUser);
            return deletedUser;
        } else {
            throw new UserNotFoundException("User not found with id : " + userId);//create my own exception
        }
    }

}
