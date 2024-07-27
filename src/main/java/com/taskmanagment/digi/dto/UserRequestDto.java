package com.taskmanagment.digi.dto;
/**
 * Step 2
 * UserRequestDto class is the layer that is connected directly with the user
 * It is used here to be sent as the body of the POST method , that's why we did the validation thingy here
 * From here going to be checked .if it does not meet the constraints, an exception will be thrown to not go further
 */

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;


@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
/**
 * provides a template for the required data to be filled
 */
public class UserRequestDto {
    @NotNull(message = "username ")
    private String username;

    @NotNull(message = "Email ")
    @Email(message = "invalid Email")
    private String email;
}
/**
 * {
 *   "username": "user",
 *   "email": "user@gmail.com"
 * }
 */