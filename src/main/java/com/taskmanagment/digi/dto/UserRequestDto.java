package com.taskmanagment.digi.dto;
/**
 * Step 2
 * UserRequestDto class is the layer that is connected directly with the user
 * It is used here to be sent as the body of the POST method , that's why we did the validation thingy here
 * From here going to be checked .if it does not meet the constraints, an exception will be thrown to not go further
 */

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
/**
 * provides a template for the required data to be filled
 * throw a MethodArgumentNotValidException If filled with wrong data
 * (the error handled inside com.taskmanagment.digi.exception.handler / Via handleInvalidArgument() method )
 */
public class UserRequestDto {
    @NotNull(message = "username ")
    private String username;
    @Email(message = "invalid Email")
    private String email;
}
