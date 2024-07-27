package com.taskmanagment.digi.controller;

import com.taskmanagment.digi.entities.Task;
import com.taskmanagment.digi.exception.type.IdNotFoundException;
import com.taskmanagment.digi.service.TaskServices;
import com.taskmanagment.digi.service.TaskUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks/Users")
public class TaskUserController {
    @Autowired
    private TaskServices taskServices;
    @Autowired
    private TaskUserService taskUserService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Task>> getTaskAssociatedWithUser(@PathVariable Long id ) throws IdNotFoundException {
        return ResponseEntity.ok(taskUserService.getAllTasks(id));
    }
}
