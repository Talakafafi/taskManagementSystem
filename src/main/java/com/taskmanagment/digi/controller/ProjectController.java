package com.taskmanagment.digi.controller;

import com.taskmanagment.digi.dto.ProjectRequestDto;
import com.taskmanagment.digi.dto.TaskUserRequestDto;
import com.taskmanagment.digi.entities.Project;
import com.taskmanagment.digi.exception.type.IdNotFoundException;
import com.taskmanagment.digi.exception.type.UnmatchedUsers;
import com.taskmanagment.digi.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private  ProjectService projectService;

    @PostMapping("/addProject")
    public Project  addProject(@RequestBody @Valid ProjectRequestDto projectRequestDto){
         return projectService.addProject(projectRequestDto);
    }

    @PostMapping("/addTask/{id}")
    public Project  addTask (@RequestBody @Valid  TaskUserRequestDto taskUserRequestDto, @PathVariable Long id ) throws UnmatchedUsers, IdNotFoundException {
        return projectService.addNewTask(id,taskUserRequestDto);
    }

    @GetMapping("/{id}")
    public Project  getproject (@PathVariable Long id ) throws  IdNotFoundException {
        return projectService.getProject(id);
    }



}
