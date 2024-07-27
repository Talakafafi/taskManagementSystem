package com.taskmanagment.digi.exception.handler;
/**
 * to get proper error message
 * the following methods redirect  the errors to an appropriate method that has a
 * suitable logic to handle the error or  make it more readable
 *
 */

import com.taskmanagment.digi.exception.type.IdNotFoundException;
import com.taskmanagment.digi.exception.type.UnmatchedUsers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    /**
     * handles the error thrown by the validation annotation
     * @param ex
     * @return Map<String,String>  return the error field (witch error cause the error )
     *                                    with the message specified in the annotation
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)//that means whenever find this error redirect it to the following method
    public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException ex){
        Map<String,String> errorMap=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->{
            errorMap.put(error.getField(),error.getDefaultMessage()); //
        });
        return errorMap;
    }

    /**
     * handles the error thrown when the user insert id for a user does not exist
     * @param ex
     * @return  Map<String , String> will return errorMessage with the message you pass when invoke the method
     */

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IdNotFoundException.class)
    public Map<String , String> handleIDNotFoundException(IdNotFoundException ex){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("errorMessage",ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UnmatchedUsers.class)
    public Map<String , String> UnmatchedUserExceptionHandler (UnmatchedUsers ex){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("errorMessage",ex.getMessage());
        return errorMap;
    }
}
