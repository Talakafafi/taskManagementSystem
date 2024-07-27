package com.taskmanagment.digi.service;

import com.taskmanagment.digi.dto.ProjectRequestDto;
import com.taskmanagment.digi.dto.TaskUserRequestDto;
import com.taskmanagment.digi.entities.*;
import com.taskmanagment.digi.exception.type.IdNotFoundException;
import com.taskmanagment.digi.exception.type.UnmatchedUsers;
import com.taskmanagment.digi.repository.ProjectRepository;
import com.taskmanagment.digi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskUserService taskUserService;
    @Autowired
    TaskRepository taskRepository;

    /**
     * adds new project
     * @param projectRequestDto contains the data needed to fill the project object
     * @return the added project
     */
    public Project addProject(ProjectRequestDto projectRequestDto) {
        Project project = new Project();
        project.setName(projectRequestDto.getName());
        return projectRepository.save(project);
    }

    /**
     * Adds new task inside the project
     * @param projectId
     * @param task contains the data needed to fill the project object
     * @return the project that has added tasks
     * @throws IdNotFoundException if the project id does not exist
     * @throws UnmatchedUsers if one of the fields contain wrong value
     */
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

    /**
     * removes the project
     * @param projectId
     * @throws IdNotFoundException
     */
    public void removeProject(Long projectId)throws IdNotFoundException{
        Project project = projectRepository.findByProjectId(projectId);
        projectRepository.delete(project);
    }

    /**
     * returns the number of tasks that have specific priority value
     * @param taskPriority
     * @return
     */
    public Long numberOfTasksWithPriority(TaskPriority taskPriority){
           return taskRepository.findAll().stream().filter(x -> x.getPriority().equals(taskPriority)).count();
        }

    /**
     * returns the number of tasks that have specific status level
     * @param taskStatus
     * @return
     */
    public Long numberOfTasksWithStatus(TaskStatus taskStatus){
            return  taskRepository.findAll().stream().filter(x -> x.getStatus().equals(taskStatus)).count();
        }

        public Project getProject(Long projectId){
        return projectRepository.findByProjectId(projectId);

        }


    }

