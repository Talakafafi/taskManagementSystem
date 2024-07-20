package com.taskmanagment.digi.service;

import com.taskmanagment.digi.dto.TaskFilterationDto;
import com.taskmanagment.digi.dto.TaskRequestDto;
import com.taskmanagment.digi.entities.Task;
import com.taskmanagment.digi.exception.type.TaskNotFoundException;
import com.taskmanagment.digi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Stream;

@Service
public class TaskServices {

    @Autowired
    private TaskRepository taskRepository;

    public Task addTask(TaskRequestDto taskRequestDto){
        Task task =Task
                .build(0,taskRequestDto.getTitle(),taskRequestDto.getDescription() ,taskRequestDto.getStatus()
                ,taskRequestDto.getPriority(),taskRequestDto.getDueDate(),null);
        return  taskRepository.save(task);
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task getTask(int taskId) throws TaskNotFoundException {
        Task task=   taskRepository.findByTaskId(taskId);
        if(task!=null){
            return task;
        }else{
            throw new TaskNotFoundException("Task not found with id : "+taskId);//create my own exception
        }
    }

    public Task updateUser(int taskId ,TaskRequestDto taskDto ) throws TaskNotFoundException {
        Task updatedTask = taskRepository.findByTaskId(taskId);
        if(updatedTask!=null){
           updatedTask.setTitle((taskDto.getTitle()==null)?updatedTask.getTitle():taskDto.getTitle());
           updatedTask.setDescription((taskDto.getDescription()==null)?updatedTask.getDescription():taskDto.getDescription());
           updatedTask.setStatus((taskDto.getStatus()==null?updatedTask.getStatus():taskDto.getStatus()));
           updatedTask.setPriority((taskDto.getPriority()==null?updatedTask.getPriority():taskDto.getPriority()));
           updatedTask.setDueDate((taskDto.getDueDate()==null?updatedTask.getDueDate():taskDto.getDueDate()));

            return updatedTask;
        }else{
            throw new TaskNotFoundException("Task not found with id : "+taskId);
        }
    }

    public Task removeTask(int taskId) throws TaskNotFoundException {
        Task deletedTask = taskRepository.findByTaskId(taskId);
        if (deletedTask != null) {
            taskRepository.delete(deletedTask);
            return deletedTask;
        } else {
            throw new TaskNotFoundException("Task not found with id : " + taskId);//create my own exception
        }
    }

    public List<Task> taskFilteration(TaskFilterationDto taskFilterationDto) {
        Stream<Task> streamFromList = taskRepository.findAll().stream();
        if (taskFilterationDto.getStatus()!=null) {
            streamFromList = streamFromList.filter((task) -> {
                return task.getStatus().equals(taskFilterationDto.getStatus());
            });
        }

        if (taskFilterationDto.getPriority()!=null) {
            streamFromList = streamFromList.filter((task) -> {
                return task.getPriority().equals(taskFilterationDto.getPriority());
            });
        }

        if (taskFilterationDto.getDueDate()!=null) {
            streamFromList = streamFromList.filter((task) -> {
                return task.getDueDate().equals(taskFilterationDto.getDueDate());
            });
        }


        return  streamFromList.toList();
    }
}
