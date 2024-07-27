package com.taskmanagment.digi.service;

import com.taskmanagment.digi.dto.ProjectRequestDto;
import com.taskmanagment.digi.dto.TaskRequestDto;
import com.taskmanagment.digi.dto.TaskUserRequestDto;
import com.taskmanagment.digi.entities.*;
import com.taskmanagment.digi.exception.type.IdNotFoundException;
import com.taskmanagment.digi.exception.type.UnmatchedUsers;
import com.taskmanagment.digi.repository.ProjectRepository;
import com.taskmanagment.digi.repository.TaskRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private Task_UserService taskUserService;
    @Autowired
    TaskRepository taskRepository;

    public Project addProject(ProjectRequestDto projectRequestDto) {
        Project project = new Project();
        project.setName(projectRequestDto.getName());
        return projectRepository.save(project);
    }

    public Project addNewTask(Long projectId, TaskUserRequestDto task) throws IdNotFoundException, UnmatchedUsers {
        Project project = projectRepository.findByProjectId(projectId);
        if (project == null) throw new IdNotFoundException(Project.class.getName(), projectId);
        else {
            Task newTask = taskUserService.addTask(task);
            newTask.setProject(project);
            taskRepository.save(newTask);

            return project;
        }
    }

    public void removeProject(Long projectId)throws IdNotFoundException{
        Project project = projectRepository.findByProjectId(projectId);
        projectRepository.delete(project);
    }

    public Long numberOfTasksWithPriority(TaskPriority taskPriority){
           return taskRepository.findAll().stream().filter(x -> x.getPriority().equals(taskPriority)).count();
        }

        public Long numberOfTasksWithStatus(TaskStatus taskStatus){
            return  taskRepository.findAll().stream().filter(x -> x.getStatus().equals(taskStatus)).count();
        }


    }

