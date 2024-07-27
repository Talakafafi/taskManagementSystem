package com.taskmanagment.digi.controller;

import com.taskmanagment.digi.dto.TaskFilterationDto;
import com.taskmanagment.digi.dto.TaskRequestDto;
import com.taskmanagment.digi.dto.TaskUserRequestDto;
import com.taskmanagment.digi.entities.Task;
import com.taskmanagment.digi.entities.User;
import com.taskmanagment.digi.exception.type.IdNotFoundException;
import com.taskmanagment.digi.exception.type.UnmatchedUsers;
import com.taskmanagment.digi.service.TaskServices;
import com.taskmanagment.digi.service.TaskUserService;
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
    private TaskUserService taskUserService;


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

    @PostMapping("/filter")
    public ResponseEntity<List<Task>> FilterTask (@RequestBody  TaskFilterationDto taskFilterationDto){
        return ResponseEntity.ok(taskServices.taskFiltration(taskFilterationDto));
    }

    @GetMapping("/SortByPriority")
    public  ResponseEntity<List<Task>> SortByPriority(){
        return ResponseEntity.ok(taskServices.sort(2));
    }

    @GetMapping("/SortByStatus")
    public  ResponseEntity<List<Task>> SortByStatus(){
        return ResponseEntity.ok(taskServices.sort(1));
    }

    @GetMapping("/SortByDate")
    public  ResponseEntity<List<Task>> SortByDate(){
        return ResponseEntity.ok(taskServices.sort(3));
    }

    @GetMapping("/removeTask/{id}")//the get does not have a body that why we pass the required parameter with the path or build it as a post
    public void removeTak(@PathVariable  Long id ) throws IdNotFoundException {
      taskServices.removeTask(id);
    }




}
