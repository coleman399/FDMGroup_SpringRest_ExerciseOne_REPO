package com.fdmgroup.spring_rest_exercise_one.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.fdmgroup.spring_rest_exercise_one.exception.UserAlreadyExistsException;
import com.fdmgroup.spring_rest_exercise_one.exception.UserNotFoundException;
import com.fdmgroup.spring_rest_exercise_one.model.User;
import com.fdmgroup.spring_rest_exercise_one.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"","index","/"})
    public List<User> getUsers() {
        return userService.retrieveUsers();
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) throws UserNotFoundException {
        User foundUser = userService.retrieveUser(username);
        return new ResponseEntity<User>(foundUser, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws UserAlreadyExistsException {
        User createdUser = userService.generateUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(createdUser.getUsername()).toUri();
        return ResponseEntity.created(location).body(createdUser);
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user, @PathVariable String username) throws UserNotFoundException {
        User updatedUser = userService.amendUser(user, username);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(updatedUser.getUsername()).toUri();
        return ResponseEntity.created(location).body(updatedUser);
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable("username") String username) throws UserNotFoundException {
        userService.removeUser(username);
    }

}
