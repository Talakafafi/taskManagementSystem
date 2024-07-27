package com.taskmanagment.digi.dto;

/**
 used as the body of projects/addProject API

 {
 "name": " Project Name"
 }

 */

import lombok.*;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class ProjectRequestDto {
    private String name;
}
