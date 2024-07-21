package com.taskmanagment.digi.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.taskmanagment.digi.dto.TaskFilterationDto;
import com.taskmanagment.digi.entities.Project;
import com.taskmanagment.digi.entities.Task;
import com.taskmanagment.digi.entities.TaskPriority;
import com.taskmanagment.digi.entities.TaskStatus;
import com.taskmanagment.digi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServices taskService;

         Task task1 = Task.build(0,"Task1","Description1",TaskStatus.INPROCESSING,
                TaskPriority.HIGH,LocalDate.parse("2023-05-08"),new Project());

         Task task2 = Task.build(0,"Task2","Description2",TaskStatus.COMPLETED,
                TaskPriority.INTERMEDIATE,LocalDate.parse("2024-07-20"),new Project());

         Task task3 = Task.build(0,"Task3","Description3",TaskStatus.INPROCESSING,
                TaskPriority.HIGH,LocalDate.parse("2024-07-25"),new Project());


     List<Task>  tasks = Arrays.asList(task1, task2, task3);


    @Test
    void testTaskFilterationByStatus() {
        TaskFilterationDto filterDto = new TaskFilterationDto();
        filterDto.setStatus(TaskStatus.INPROCESSING);


        when(taskRepository.findAll()).thenReturn(tasks);//it means if you invoke findAll() the output of it must be tasks List , to help service with its job
        List<Task> filteredTasks = taskService.taskFilteration(filterDto);

        assertEquals(2, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getTitle());
        assertEquals("Task3", filteredTasks.get(1).getTitle());
    }

    @Test
    void testTaskFilterationByPriority() {

        TaskFilterationDto filterDto = new TaskFilterationDto();
        filterDto.setPriority(TaskPriority.HIGH);

        when(taskRepository.findAll()).thenReturn(tasks);
        List<Task> filteredTasks = taskService.taskFilteration(filterDto);

        assertEquals(2, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getTitle());
        assertEquals("Task3", filteredTasks.get(1).getTitle());
    }

    @Test
    void testTaskFilterationByDueDate() {

        TaskFilterationDto filterDto = new TaskFilterationDto();
        filterDto.setDueDate(LocalDate.parse("2023-05-08"));

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> filteredTasks = taskService.taskFilteration(filterDto);

        assertEquals(1, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getTitle());
    }

    @Test
    void testTaskFilterationByAllFilters() {

        TaskFilterationDto filterDto = new TaskFilterationDto();
        filterDto.setStatus(TaskStatus.INPROCESSING);
        filterDto.setPriority(TaskPriority.HIGH);
        filterDto.setDueDate(LocalDate.parse("2023-05-08"));


        when(taskRepository.findAll()).thenReturn(tasks);
        List<Task> filteredTasks = taskService.taskFilteration(filterDto);

        assertEquals(1, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getTitle());
    }

    @Test
    void testTaskFilterationNoFilters() {

        TaskFilterationDto filterDto = new TaskFilterationDto();


        when(taskRepository.findAll()).thenReturn(tasks);
        List<Task> filteredTasks = taskService.taskFilteration(filterDto);


        assertEquals(3, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getTitle());
        assertEquals("Task2", filteredTasks.get(1).getTitle());
        assertEquals("Task3", filteredTasks.get(2).getTitle());
    }
}