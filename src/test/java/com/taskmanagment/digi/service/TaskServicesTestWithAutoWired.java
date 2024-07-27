package com.taskmanagment.digi.service;

import com.taskmanagment.digi.dto.TaskFilterationDto;
import com.taskmanagment.digi.dto.TaskRequestDto;
import com.taskmanagment.digi.dto.TaskUpdateRequestDto;
import com.taskmanagment.digi.entities.*;
import com.taskmanagment.digi.exception.type.IdNotFoundException;
import com.taskmanagment.digi.exception.type.UnmatchedUsers;
import com.taskmanagment.digi.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServicesTestWithAutoWired {

    @Autowired
        TaskRepository taskRepository;

        @Autowired
      TaskServices taskService;


    List<Task> tasks(){
        Task task1 = new  Task("Task1","Description1", TaskStatus.INPROCESSING,
                TaskPriority.HIGH, LocalDate.parse("2023-05-08"));

        Task task2 =new  Task("Task2","Description2",TaskStatus.COMPLETED,
                TaskPriority.INTERMEDIATE,LocalDate.parse("2024-07-20"));

        Task task3 = new Task("Task3","Description3",TaskStatus.INPROCESSING,
                TaskPriority.HIGH,LocalDate.parse("2024-07-25"));


        List<Task> tasks = Arrays.asList(task1, task2, task3);

        return tasks ; }

    Long taskId(){
        Task task1 = new  Task("Task1","Description1", TaskStatus.INPROCESSING,
                TaskPriority.HIGH, LocalDate.parse("2023-05-08"));
        task1=taskRepository.save(task1);
        return  task1.getTaskId();
    }

    @Test
    @Transactional
    void addTask_AddsAssignedTask_returnsTrue()  {
        TaskRequestDto task =  TaskRequestDto.build("Task1","Description1", TaskStatus.INPROCESSING,
                TaskPriority.HIGH, LocalDate.parse("2023-05-08"),null );

        Task addedTask = taskService.addTask(task);
        assertEquals(addedTask.getTitle(),"Task1");

    }

    @Test
    @Transactional
    void removeTask_RemovesAssignedTask_returnsNull() throws IdNotFoundException {
       Task newTask = taskRepository.save(tasks().get(0));
       Long taskId = newTask.getTaskId();

        taskService.removeTask(taskId);
        Task deletedTask = taskRepository.findByTaskId(taskId);
        assertNull(deletedTask);

    }
    @Test
    void removeTask_RemovesTaskWithNotExistingID_ThrowsIdNotFoundException() throws IdNotFoundException {
        assertThrows(IdNotFoundException.class, () -> {
            taskService.removeTask(30L);
        });
    }

    @Test
    @Transactional
    void updateTask_UpdatesTheTitleForExistingTask_ReturnsTrue() throws IdNotFoundException {
        Long taskId = taskId();

        TaskUpdateRequestDto taskRequestDto = new TaskUpdateRequestDto();
        taskRequestDto.setTitle("new title ");

        Task updatedTask = taskService.updateUser(taskId, taskRequestDto);

        assertEquals("new title ", updatedTask.getTitle());
    }
    @Test
    @Transactional
    void updateTask_UpdatesAllFieldsForExistingTask_ReturnsTrue() throws IdNotFoundException {
        Long taskId = taskId();

        TaskUpdateRequestDto taskRequestDto =  TaskUpdateRequestDto.build("Task5","Description5", TaskStatus.COMPLETED,
                TaskPriority.LOW, LocalDate.parse("2023-05-12"));

        Task updatedTask = taskService.updateUser(taskId, taskRequestDto);

        assertEquals(taskRequestDto.getTitle(), updatedTask.getTitle(), "The title should be updated.");
        assertEquals(taskRequestDto.getDescription(), updatedTask.getDescription(), "The description should be updated.");
        assertEquals(taskRequestDto.getStatus(), updatedTask.getStatus(), "The status should be updated.");
        assertEquals(taskRequestDto.getPriority(), updatedTask.getPriority(), "The priority should be updated.");
        assertEquals(taskRequestDto.getDueDate(), updatedTask.getDueDate(),"The due date should be updated.");
    }



    @Test
    @Transactional
    void updateTask_UpdatesTheTitleForNotExistingId_ThrowsIdNotFoundExceptionException() throws IdNotFoundException {
        TaskUpdateRequestDto taskRequestDto = new TaskUpdateRequestDto();
        taskRequestDto.setTitle("new title ");

        assertThrows(IdNotFoundException.class, () -> {
            taskService.updateUser(30L, taskRequestDto);
        });
    }


    @Test
    @Transactional
    void getAllTasks_getsAllSavedTasks_ReturnsTrue() {
        List<Task> alltasks = tasks();
        assertEquals(3, alltasks.size());
    }

        @Test
        @Transactional
        void taskFiltration_GetsTheTasksAWithSpecificStatus_ReturnsTrue() {
            TaskFilterationDto filterDto = new TaskFilterationDto();
            filterDto.setStatus(TaskStatus.INPROCESSING);


            taskRepository.saveAll(tasks());
            List<Task> filteredTasks = taskService.taskFiltration(filterDto);

            assertEquals(2, filteredTasks.size());
            assertEquals("Task1", filteredTasks.get(0).getTitle());
            assertEquals("Task3", filteredTasks.get(1).getTitle());
        }

        @Test
        @Transactional
        void taskFiltration_GetsTheTasksAWithSpecificPriority_ReturnsTrue() {

            TaskFilterationDto filterDto = new TaskFilterationDto();
            filterDto.setPriority(TaskPriority.HIGH);


            taskRepository.saveAll(tasks());
            List<Task> filteredTasks = taskService.taskFiltration(filterDto);

            assertEquals(2, filteredTasks.size());
            assertEquals("Task1", filteredTasks.get(0).getTitle());
            assertEquals("Task3", filteredTasks.get(1).getTitle());
        }

        @Test
        @Transactional
        void taskFiltration_GetsTheTasksAWithSpecificDate_ReturnsTrue() {

            TaskFilterationDto filterDto = new TaskFilterationDto();
            filterDto.setDueDate(LocalDate.parse("2023-05-08"));


            taskRepository.saveAll(tasks());

            List<Task> filteredTasks = taskService.taskFiltration(filterDto);

            assertEquals(1, filteredTasks.size());
            assertEquals("Task1", filteredTasks.get(0).getTitle());
        }

        @Test
        @Transactional
        void taskFiltration_GetsTheTasksWithAllFilters_ReturnsTrue() {

            TaskFilterationDto filterDto = new TaskFilterationDto();
            filterDto.setStatus(TaskStatus.INPROCESSING);
            filterDto.setPriority(TaskPriority.HIGH);
            filterDto.setDueDate(LocalDate.parse("2023-05-08"));


            taskRepository.saveAll(tasks());
            List<Task> filteredTasks = taskService.taskFiltration(filterDto);

            assertEquals(1, filteredTasks.size());
            assertEquals("Task1", filteredTasks.get(0).getTitle());
        }

        @Test
        @Transactional
        void taskFiltration_GetsTheTasksAWithNoFilters_ReturnsTrue() {



            List<Task> tasks = taskRepository.findAll();
            taskRepository.deleteAll();
            taskRepository.saveAll(tasks());

            TaskFilterationDto filterDto = new TaskFilterationDto();
            List<Task> filteredTasks = taskService.taskFiltration(filterDto);


            assertEquals(3, filteredTasks.size(),"size is wrong ");
            assertEquals("Task1", filteredTasks.get(0).getTitle());
            assertEquals("Task2", filteredTasks.get(1).getTitle());
            assertEquals("Task3", filteredTasks.get(2).getTitle());
        }
    }