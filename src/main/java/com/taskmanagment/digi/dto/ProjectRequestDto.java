package com.taskmanagment.digi.dto;

import com.taskmanagment.digi.entities.Task;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class ProjectRequestDto {
    private String name;
}
/**
 * {
 *   "name": "Sample Project Name"
 * }
 */