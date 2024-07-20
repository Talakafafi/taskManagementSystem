package com.taskmanagment.digi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.taskmanagment.digi.dto.UserRequestDto;
import com.taskmanagment.digi.entities.User;
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

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void addUserTest() {
        User user = User.build(0, "user", "user@gmail.com");

        when(userRepository.save(any(User.class))).thenReturn(user);//fill the userRepository to help service do its job

        UserRequestDto userRequestDto = UserRequestDto.build("user","user@gmail.com");
        User isertedUser = userService.addUser(userRequestDto);



        assertEquals("user@gmail.com", isertedUser.getEmail());
        assertEquals("user", isertedUser.getUsername());

    }



}