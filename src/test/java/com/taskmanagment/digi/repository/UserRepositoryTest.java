package com.taskmanagment.digi.repository;

import com.taskmanagment.digi.entities.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository ;
    @Autowired
    TaskRepository taskRepository;
    @Test
    @Transactional
    @Rollback
    public void save_saveUserUsingItsRepository_returnTrue(){
        //Arrange
        User user=new User("user","user@gmail.com");

        //Act
        User newUser = userRepository.save(user);

        //Assert
        assertNotNull(newUser);
    }


    @Test
    @Transactional //@Transactional annotation ensures that the Hibernate session remains open for the duration of the test
    @Rollback
    public void findByUserId_findExistingUserByItsId_returnTrue() {
        // Arrange
        User user = new User("user", "user@gmail.com");
        User savedUser = userRepository.save(user);

        // Act
        User foundUser = userRepository.findByUserId(savedUser.getUserId());

        // Assert
        assertEquals(savedUser, foundUser);
    }

    @Test
    @Transactional
    @Rollback
    public void delete_deleteUserUsingItsRepository_returnNull() {
        User user = new User("user", "user@gmail.com");
        User newUser = userRepository.save(user);
        Long newUserUserId =newUser.getUserId();
        userRepository.delete(newUser);

        assertNull(userRepository.findByUserId(newUserUserId));
    }

    @Test
    @Transactional
    @Rollback
    public void findAll_listAllExistingUser_returnTrue(){
        taskRepository.deleteAll();
         userRepository.deleteAll();

        User user1 = new User("user", "user@gmail.com");
        User user2 = new User("user2", "user2@gmail.com");
        userRepository.save(user1);
        userRepository.save(user2);

        List<User> foundUsers = userRepository.findAll();

        assertEquals(2, foundUsers.size());


}
}