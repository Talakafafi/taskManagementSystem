package com.taskmanagment.digi.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.taskmanagment.digi.dto.TaskFilterationDto;
import com.taskmanagment.digi.dto.TaskRequestDto;
import com.taskmanagment.digi.entities.*;
import com.taskmanagment.digi.exception.type.IdNotFoundException;
import com.taskmanagment.digi.exception.type.UnmatchedUsers;
import com.taskmanagment.digi.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServices taskService;


    @Test
    void addTask_AddsAValidTask_ReturnsTheAddedTask() {
        Task MockAddedTask = new Task("Task1", "Description1", TaskStatus.INPROCESSING,
                TaskPriority.HIGH, LocalDate.parse("2023-05-08"));
        when(taskRepository.save(any(Task.class))).thenReturn(MockAddedTask);

        TaskRequestDto task = TaskRequestDto.build("Task1", "Description1", TaskStatus.INPROCESSING,
                TaskPriority.HIGH, LocalDate.parse("2023-05-08"),null);
        Task addedTask = taskService.addTask(task);

        assertEquals(addedTask.getTitle(), MockAddedTask.getTitle());
    }

    @Test
    void removeTask_RemovesAssignedTask_returnsNull() throws IdNotFoundException {
        Task task= new Task();
        when(taskRepository.findByTaskId(task.getTaskId())).thenReturn(task);
        taskService.removeTask(task.getTaskId());
        verify(taskRepository).delete(task);
    }

    @Test
    void removeTask_RemovesTaskWithNotExistingID_ThrowsIdNotFoundException() throws IdNotFoundException {
        when(taskRepository.findByTaskId(2L)).thenReturn(null);
        assertThrows(IdNotFoundException.class, () -> {
            taskService.removeTask(2L);
        });
    }

    @Test
    void updateTask_UpdatesTheTitleForExistingTask_ReturnsTrue() throws IdNotFoundException {
        Long taskId = 1L;
        Task task = new Task("Task5", "Description5", TaskStatus.COMPLETED, TaskPriority.LOW, LocalDate.parse("2023-05-12"));
        task.setTaskId(taskId);
        when(taskRepository.findByTaskId(taskId)).thenReturn(task);

        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setTitle("new title ");
            Task updatedTask = taskService.updateUser(taskId, taskRequestDto);

        assertEquals("new title ", updatedTask.getTitle());
        verify(taskRepository).save(task);
    }

    @Test
    void updateTask_UpdatesAllFieldsForExistingTask_ReturnsTrue() throws IdNotFoundException {
        Task task= new Task();
        when(taskRepository.findByTaskId(task.getTaskId())).thenReturn(task);

        TaskRequestDto taskRequestDto = TaskRequestDto.build("Task5", "Description5", TaskStatus.COMPLETED,
                TaskPriority.LOW, LocalDate.parse("2023-05-12"),null);
        Task updatedTask = taskService.updateUser(task.getTaskId(), taskRequestDto);

        assertEquals(taskRequestDto.getTitle(), updatedTask.getTitle(), "The title should be updated.");
        assertEquals(taskRequestDto.getDescription(), updatedTask.getDescription(), "The description should be updated.");
        assertEquals(taskRequestDto.getStatus(), updatedTask.getStatus(), "The status should be updated.");
        assertEquals(taskRequestDto.getPriority(), updatedTask.getPriority(), "The priority should be updated.");
        assertEquals(taskRequestDto.getDueDate(), updatedTask.getDueDate(), "The due date should be updated.");
    }


    @Test
    void updateTask_UpdatesTheTitleForNotExistingId_ThrowsIdNotFoundExceptionException() throws IdNotFoundException {
      when(taskRepository.findByTaskId(2L)).thenReturn(null);

        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setTitle("new title ");

        assertThrows(IdNotFoundException.class, () -> {
            taskService.updateUser( 2L, taskRequestDto);
        });
    }


    @Test
    void getAllTasks_getsAllSavedTasks() {
        when(taskRepository.findAll()).thenReturn(tasks());
        List<Task> allTasks = taskService.getAllTasks();

        assertEquals(3, allTasks.size());
    }

    ////////////////////////////
    @Test
    void taskFiltration_ByStatusInProcessing_ReturnsCorrectTasks() {
        TaskFilterationDto filterDto = new TaskFilterationDto();
        filterDto.setStatus(TaskStatus.INPROCESSING);


        when(taskRepository.findAll()).thenReturn(tasks());//it means if you invoke findAll() the output of it must be tasks List , to help service with its job
        List<Task> filteredTasks = taskService.taskFiltration(filterDto);

        assertEquals(2, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getTitle());
        assertEquals("Task3", filteredTasks.get(1).getTitle());
    }

    @Test
    void taskFiltration_ByPriorityHigh_ReturnsCorrectTasks() {

        TaskFilterationDto filterDto = new TaskFilterationDto();
        filterDto.setPriority(TaskPriority.HIGH);

        when(taskRepository.findAll()).thenReturn(tasks());
        List<Task> filteredTasks = taskService.taskFiltration(filterDto);

        assertEquals(2, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getTitle());
        assertEquals("Task3", filteredTasks.get(1).getTitle());
    }

    @Test
    void taskFiltration_ByDueDate2023_5_8_ReturnsCorrectTasks() {

        TaskFilterationDto filterDto = new TaskFilterationDto();
        filterDto.setDueDate(LocalDate.parse("2023-05-08"));

        when(taskRepository.findAll()).thenReturn(tasks());

        List<Task> filteredTasks = taskService.taskFiltration(filterDto);

        assertEquals(1, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getTitle());
    }

    @Test
    void taskFiltration_ByStatusInProcessing_PriorityHigh_ByDueDate2023_5_8_ReturnsCorrectTasks() {

        TaskFilterationDto filterDto = new TaskFilterationDto();
        filterDto.setStatus(TaskStatus.INPROCESSING);
        filterDto.setPriority(TaskPriority.HIGH);
        filterDto.setDueDate(LocalDate.parse("2023-05-08"));


        when(taskRepository.findAll()).thenReturn(tasks());
        List<Task> filteredTasks = taskService.taskFiltration(filterDto);

        assertEquals(1, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getTitle());
    }

    @Test
    void taskFiltration_NoFilter_ReturnsAllTasks() {

        TaskFilterationDto filterDto = new TaskFilterationDto();


        when(taskRepository.findAll()).thenReturn(tasks());
        List<Task> filteredTasks = taskService.taskFiltration(filterDto);


        assertEquals(3, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getTitle());
        assertEquals("Task2", filteredTasks.get(1).getTitle());
        assertEquals("Task3", filteredTasks.get(2).getTitle());
    }


    List<Task> tasks() {
        Task task1 = new Task("Task1", "Description1", TaskStatus.INPROCESSING,
                TaskPriority.HIGH, LocalDate.parse("2023-05-08"));

        Task task2 = new Task("Task2", "Description2", TaskStatus.COMPLETED,
                TaskPriority.INTERMEDIATE, LocalDate.parse("2024-07-20"));

        Task task3 = new Task("Task3", "Description3", TaskStatus.INPROCESSING,
                TaskPriority.HIGH, LocalDate.parse("2024-07-25"));


        List<Task> tasks = Arrays.asList(task1, task2, task3);
        return tasks;
    }
}