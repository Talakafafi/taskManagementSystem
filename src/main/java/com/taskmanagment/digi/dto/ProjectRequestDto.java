package com.taskmanagment.digi.dto;

import com.taskmanagment.digi.entities.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class ProjectRequestDto {
    private String name;
    private List<Task> tasks;
}
