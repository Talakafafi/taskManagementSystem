package com.taskmanagment.digi.service;
/**
 * this service is for the operation that related to both userRepository and taskRepository
 */

import com.taskmanagment.digi.dto.TaskUserRequestDto;
import com.taskmanagment.digi.entities.Task;
import com.taskmanagment.digi.entities.User;
import com.taskmanagment.digi.exception.type.UnmatchedUsers;
import com.taskmanagment.digi.repository.TaskRepository;
import com.taskmanagment.digi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TaskUserService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Adds task here you pass list of Ids and first will check if the ids are exist in the database or not
     *
     * @param taskRequestDto
     * @return
     * @throws UnmatchedUsers
     */
    public Task addTask(TaskUserRequestDto taskRequestDto) throws UnmatchedUsers {
        Task task;
        if (taskRequestDto.getUsersId().isEmpty()) {
            task = Task.build(null, taskRequestDto.getTitle(), taskRequestDto.getDescription(), taskRequestDto.getStatus()
                    , taskRequestDto.getPriority(), taskRequestDto.getDueDate(), null, null);
            return taskRepository.save(task);
        } else {
            List<User> matchedUsers = userRepository.findAll().stream()
                    .filter(user -> taskRequestDto.getUsersId().contains(user.getUserId()))
                    .toList();

            if (matchedUsers.isEmpty()) {
                throw new UnmatchedUsers();
            } else {
                task = Task.build(null, taskRequestDto.getTitle(), taskRequestDto.getDescription(), taskRequestDto.getStatus()
                        , taskRequestDto.getPriority(), taskRequestDto.getDueDate(), null, matchedUsers);


                return taskRepository.save(task);
            }
        }

    }

    /**
     * g=returns all tasks with all associated users
     *
     * @param userId
     * @return
     */

    public List<Task> getAllTasks(Long userId) {
        Stream<Task> streamFromList = taskRepository.findAll().stream();
        streamFromList = streamFromList.filter((task -> task.getUsers().contains(userRepository.findByUserId(userId))));
        return streamFromList.toList();

    }
}
