package com.taskmanagment.digi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.taskmanagment.digi.dto.UserRequestDto;
import com.taskmanagment.digi.entities.Task;
import com.taskmanagment.digi.entities.User;
import com.taskmanagment.digi.exception.type.UserNotFoundException;
import com.taskmanagment.digi.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void addUserTest() {
        User user = User.build(0, "user", "user@gmail.com");

        // Mock userRepository.save to return the pre-defined user
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserRequestDto userRequestDto = UserRequestDto.build("user","user@gmail.com");
        User isertedUser = userService.addUser(userRequestDto);



        assertEquals("user@gmail.com", isertedUser.getEmail());
        assertEquals("user", isertedUser.getUsername());

    }


    @Test
    void updateUserTest() throws UserNotFoundException {
        User user1 = User.build(0, "user", "user@gmail.com");

        when(userRepository.findByUserId(0)).thenReturn(user1);

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("newEmail@gmail.com");

        User updatedUser = userService.updateUser(0,userRequestDto);

        assertEquals("newEmail@gmail.com", updatedUser.getEmail());
        assertEquals("user", updatedUser.getUsername());
    }

    @Test
    void updateUserExceptionTest() throws UserNotFoundException {
        User user1 = User.build(0, "user", "user@gmail.com");

        when(userRepository.findByUserId(0)).thenReturn(null);

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("newEmail@gmail.com");



        assertThrows(UserNotFoundException.class , () ->{userService.updateUser(0,userRequestDto);});
    }





}