package com.taskmanagment.digi.service;

import com.taskmanagment.digi.dto.TaskFilterationDto;

import com.taskmanagment.digi.dto.TaskRequestDto;
import com.taskmanagment.digi.dto.TaskUpdateRequestDto;
import com.taskmanagment.digi.entities.Task;
import com.taskmanagment.digi.entities.User;
import com.taskmanagment.digi.exception.type.IdNotFoundException;
import com.taskmanagment.digi.exception.type.UnmatchedUsers;
import com.taskmanagment.digi.repository.TaskRepository;
import com.taskmanagment.digi.repository.UserRepository;
import org.aspectj.util.LangUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TaskServices {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * adds new task
     * here the project will be inserted as null ,
     * since this method will be used only for tasks are not associated to project
     * @param taskRequestDto contains the data needed to fill the Task object
     * @return the added task
     */
    public Task addTask(TaskRequestDto taskRequestDto) {
        Task task = Task.build(null, taskRequestDto.getTitle(), taskRequestDto.getDescription(), taskRequestDto.getStatus()
                , taskRequestDto.getPriority(), taskRequestDto.getDueDate(), null, taskRequestDto.getUsers());

        return taskRepository.save(task);
    }

    /**
     * returns all the tasks in the data base
     * @return
     */

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * returns task with specific Id
     * @param taskId
     * @return
     * @throws IdNotFoundException if the Id does not exist
     */

    public Task getTask(Long taskId) throws IdNotFoundException {
        Task task = taskRepository.findByTaskId(taskId);
        if (task != null) {
            return task;
        } else {
            throw new IdNotFoundException(Task.class.getName(), taskId);
        }
    }

    /**
     * updates the user details
     * @param taskId
     * @param taskDto contains the data to be updated ,all fields here are nullable ,
     *                if the field is null , value  remains the same
     * @return
     * @throws IdNotFoundException  if the id does not exist
     */
    public Task updateUser(Long taskId, TaskUpdateRequestDto taskDto) throws IdNotFoundException {
        Task updatedTask = taskRepository.findByTaskId(taskId);
        if (updatedTask != null) {
            updatedTask.setTitle((taskDto.getTitle() == null) ? updatedTask.getTitle() : taskDto.getTitle());
            updatedTask.setDescription((taskDto.getDescription() == null) ? updatedTask.getDescription() : taskDto.getDescription());
            updatedTask.setStatus((taskDto.getStatus() == null ? updatedTask.getStatus() : taskDto.getStatus()));
            updatedTask.setPriority((taskDto.getPriority() == null ? updatedTask.getPriority() : taskDto.getPriority()));
            updatedTask.setDueDate((taskDto.getDueDate() == null ? updatedTask.getDueDate() : taskDto.getDueDate()));

            taskRepository.save(updatedTask);
            return updatedTask;
        } else {
            throw new IdNotFoundException(Task.class.getName(), taskId);
        }
    }

    /**
     * removes task from the database
     * @param taskId
     * @throws IdNotFoundException if the id does not exist
     */

    public void removeTask(Long taskId) throws IdNotFoundException {
        Task deletedTask = taskRepository.findByTaskId(taskId);
        if (deletedTask != null) {
            taskRepository.delete(deletedTask);
        } else {
            throw new IdNotFoundException(Task.class.getName(), taskId);
        }
    }

    /**
     * filters the tasks using the passed data
     * filters the tasks depending on the status , priority and data
     * all the passed parameters are nullable
     */
    public List<Task> taskFiltration(TaskFilterationDto taskFilterationDto) {
        Stream<Task> streamFromList = taskRepository.findAll().stream();
        if (taskFilterationDto.getStatus() != null) {
            streamFromList = streamFromList.filter((task) -> {
                return task.getStatus().equals(taskFilterationDto.getStatus());
            });
        }

        if (taskFilterationDto.getPriority() != null) {
            streamFromList = streamFromList.filter((task) -> {
                return task.getPriority().equals(taskFilterationDto.getPriority());
            });
        }

        if (taskFilterationDto.getDueDate() != null) {
            streamFromList = streamFromList.filter((task) -> {
                return task.getDueDate().equals(taskFilterationDto.getDueDate());
            });
        }


        return streamFromList.toList();
    }
    /**
     * sort  by the status, priority, or due date.
     * @param sortingCriteria the datafile to sort by
     */
    public List<Task> sort(int sortingCriteria) {
        Stream<Task> streamFromList = taskRepository.findAll().stream();
        List<Task> tasks;
        switch (sortingCriteria) {
            case 1://sort by status
                tasks=streamFromList.sorted((x, y) -> x.getStatus().compareTo(y.getStatus())).toList();
                break;
            case 2://sort by priority
                tasks=streamFromList.sorted((x, y) -> x.getPriority().compareTo(y.getPriority())).toList();
                break;
            case 3://sort by date
                tasks= streamFromList.sorted((x, y) -> x.getDueDate().compareTo(y.getDueDate())).toList();
                break;

            default:tasks=streamFromList.toList();
        }return tasks;
    }
}
