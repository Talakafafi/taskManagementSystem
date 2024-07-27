package com.taskmanagment.digi.controller;

import com.taskmanagment.digi.dto.UserRequestDto;
import com.taskmanagment.digi.entities.User;
import com.taskmanagment.digi.exception.type.IdNotFoundException;
import com.taskmanagment.digi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
   private UserService userService;

    /**
     * {
     * @param userRequest
     * @return
     *
     *   throw a MethodArgumentNotValidException If filled with wrong data because of the Valid annotation
     *   (the error handled inside com.taskmanagment.digi.exception.handler / Via handleInvalidArgument() method )
     */
    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody @Valid UserRequestDto userRequest) {//if I do not have dto, It should be used ( @RequestBody User user) in that case ID is also a requirement
        return new ResponseEntity<>(userService.addUser(userRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @GetMapping("/{id}")//the get does not have a body that why we pass the required parameter with the path or build it as a post
    public ResponseEntity<User> getUser(@PathVariable  Long id ) throws IdNotFoundException {
        return ResponseEntity.ok(userService.getUser(id));
    }
}


