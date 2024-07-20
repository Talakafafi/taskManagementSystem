package com.taskmanagment.digi.service;

import com.taskmanagment.digi.dto.ProjectRequestDto;
import com.taskmanagment.digi.dto.TaskRequestDto;
import com.taskmanagment.digi.entities.Project;
import com.taskmanagment.digi.entities.Task;
import com.taskmanagment.digi.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;


    public Project addProject(ProjectRequestDto projectRequestDto) {
        Project project =new  Project();
        project.setName(projectRequestDto.getName());

        List<Task> tasks = projectRequestDto.getTasks().stream()
                .map(dto ->  Task.build(0,dto.getTitle(),dto.getDescription(),dto.getStatus()
                        ,dto.getPriority(),dto.getDueDate(),project))
                .collect(Collectors.toList());
        project.setTasks(tasks);

        tasks.forEach(task -> task.setProject(project));

        return projectRepository.save(project);
    }

    public Project addTaskToProject (int ProjectId , TaskRequestDto task){
        Project project=   projectRepository.findByTaskId(ProjectId);

        List<Task> tasks = project.getTasks().stream()
                .map(dto ->  Task.build(0,dto.getTitle(),dto.getDescription(),dto.getStatus(),dto.getPriority(),dto.getDueDate(),project))
                .collect(Collectors.toList());
        project.setTasks(tasks);

        tasks.forEach(t -> t.setProject(project));

        return project;
    }
}
