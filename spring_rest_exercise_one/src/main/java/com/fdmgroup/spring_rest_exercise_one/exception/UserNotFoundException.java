package com.fdmgroup.spring_rest_exercise_one.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception {
    
    public UserNotFoundException() {
        super("User Not Found!");
    }
    
    public UserNotFoundException(String username){
        super(username + " Not Found!");
    }
}
