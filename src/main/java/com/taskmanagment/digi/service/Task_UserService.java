package com.taskmanagment.digi.service;

import com.taskmanagment.digi.dto.TaskRequestDto;
import com.taskmanagment.digi.dto.TaskUserRequestDto;
import com.taskmanagment.digi.entities.Task;
import com.taskmanagment.digi.entities.User;
import com.taskmanagment.digi.exception.type.UnmatchedUsers;
import com.taskmanagment.digi.repository.TaskRepository;
import com.taskmanagment.digi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class Task_UserService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    public Task addTask(TaskUserRequestDto taskRequestDto ) throws UnmatchedUsers {

        Task task = Task.build(null, taskRequestDto.getTitle(), taskRequestDto.getDescription(), taskRequestDto.getStatus()
                , taskRequestDto.getPriority(), taskRequestDto.getDueDate(), null, null);

        if (!taskRequestDto.getUsersId().isEmpty()) {
            Set<User> matchedUsers = userRepository.findAll().stream()
                    .filter(user -> taskRequestDto.getUsersId().contains(user.getUserId()))
                    .collect(Collectors.toSet());

            if (matchedUsers.isEmpty()) {
                throw new UnmatchedUsers();
            } else
                task.setUsers(matchedUsers);
            return taskRepository.save(task);
        }
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks(Long userId){
        Stream<Task> streamFromList = taskRepository.findAll().stream();
        streamFromList = streamFromList.filter((task -> task.getUsers().contains(userRepository.findByUserId(userId))));
        return  streamFromList.toList();

    }
}
