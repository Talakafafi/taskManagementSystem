package com.taskmanagment.digi.controller;

import com.taskmanagment.digi.dto.TaskRequestDto;
import com.taskmanagment.digi.dto.TaskUserRequestDto;
import com.taskmanagment.digi.dto.UserRequestDto;
import com.taskmanagment.digi.entities.Task;
import com.taskmanagment.digi.entities.User;
import com.taskmanagment.digi.exception.type.IdNotFoundException;
import com.taskmanagment.digi.exception.type.UnmatchedUsers;
import com.taskmanagment.digi.service.TaskServices;
import com.taskmanagment.digi.service.Task_UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskServices taskServices;
    @Autowired
    private Task_UserService taskUserService;


    @PostMapping("/addTaskAuth")
    public ResponseEntity<Task> addTaskAuthorizedUsers(@RequestBody @Valid TaskRequestDto taskRequestDto)  {//if I do not have dto, It should be used ( @RequestBody User user) in that case ID is also a requirement
        return new ResponseEntity<>(taskServices.addTask(taskRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/addTaskUNAuth")
    public ResponseEntity<Task> addTaskUnAuthorizedUsers(@RequestBody @Valid TaskUserRequestDto taskRequestDto) throws UnmatchedUsers {
        return new ResponseEntity<>(taskUserService.addTask(taskRequestDto), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskServices.getAllTasks());
    }



}
