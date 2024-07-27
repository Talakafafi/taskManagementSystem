package com.taskmanagment.digi.service;

import com.taskmanagment.digi.dto.UserRequestDto;
import com.taskmanagment.digi.entities.Task;
import com.taskmanagment.digi.entities.User;
import com.taskmanagment.digi.exception.type.IdNotFoundException;
import com.taskmanagment.digi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void addUser_addValidUserUsingMockRepo_ReturnTrue() {
        User user = new User("user", "user@gmail.com");

        // Mock userRepository.save to return the pre-defined user
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserRequestDto userRequestDto = UserRequestDto.build("user", "user@gmail.com");
        User isertedUser = userService.addUser(userRequestDto);


        assertEquals("user@gmail.com", isertedUser.getEmail());
        assertEquals("user", isertedUser.getUsername());

    }

    @Test
    void updateUser_UpdatesTheEmailForExistingUser_ReturnsTrue() throws IdNotFoundException {
        User user1 = new User("user", "user@gmail.com");
        when(userRepository.findByUserId(0L)).thenReturn(user1);

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("newEmail@gmail.com");
        User updatedUser = userService.updateUser(0L, userRequestDto);

        assertEquals("newEmail@gmail.com", updatedUser.getEmail());
        assertEquals("user", updatedUser.getUsername());
    }

    @Test
    void updateUser_UpdatesTheNameForExistingUser_ReturnsTrue() throws IdNotFoundException {
        User user1 = new User("user", "user@gmail.com");
        when(userRepository.findByUserId(0L)).thenReturn(user1);

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("new User");
        User updatedUser = userService.updateUser(0L, userRequestDto);

        assertEquals("user@gmail.com", updatedUser.getEmail());
        assertEquals("new User", updatedUser.getUsername());
    }

    @Test
    void updateUser_UpdatesTheEmailForNotExistingId_ThrowsIdNotFoundExceptionException() throws IdNotFoundException {
        when(userRepository.findByUserId(30L)).thenReturn(null);

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("newEmail@gmail.com");

        assertThrows(IdNotFoundException.class, () -> {
            userService.updateUser(30L, userRequestDto);
        });
    }

    @Test
    void removeUser_RemovesUserWithExistingID_ReturnsTrue() throws IdNotFoundException {
      User user =new User();
      when(userRepository.findByUserId(user.getUserId())).thenReturn(user);

        userService.removeUser(user.getUserId());

        verify(userRepository).delete(user);
    }

    @Test
    void removeUser_RemovesUserWithNotExistingID_ThrowsIdNotFoundException() throws IdNotFoundException {
        when(userRepository.findByUserId(30L)).thenReturn(null);

        assertThrows(IdNotFoundException.class, () -> {
            userService.removeUser(30L);
        });
    }

    @Test
    void getAllUsers_getsAllSavedUsers_ReturnsTrue() {
        when(userRepository.findAll()).thenReturn(Stream.of( new User("user1", "user1@gmail.com"),
        new User("user2", "user2@gmail.com")).collect(Collectors.toList()));

        List<User> allUsers = userService.getAllUsers();
        assertEquals(2, allUsers.size());
    }

    @Test
    void getUser_GetsUserWithItsId_ReturnsTrue() throws IdNotFoundException {
        User user = new User("user1", "user1@gmail.com");
         when(userRepository.findByUserId(user.getUserId())).thenReturn(user);
        User getuser = userService.getUser(user.getUserId());

        assertEquals(user.getUsername(), "user1");
    }

    @Test
    void getUser_GetsUserWithNotExistingId_ThrowsIdNotFoundException() throws IdNotFoundException {
        assertThrows(IdNotFoundException.class, () -> {
            userService.getUser(30L);
        });
    }







}