package com.fdmgroup.spring_rest_exercise_one.service;

import java.util.List;

import com.fdmgroup.spring_rest_exercise_one.exception.UserAlreadyExistsException;
import com.fdmgroup.spring_rest_exercise_one.exception.UserNotFoundException;
import com.fdmgroup.spring_rest_exercise_one.model.User;
import com.fdmgroup.spring_rest_exercise_one.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> retrieveUsers() {
        return userRepository.findAll();
    }

    public User retrieveUser(String username) throws UserNotFoundException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException(username);
        } else {
            for (int i = 0; i < users.size(); i++) {
                if (i < users.size()) {
                    if (users.get(i).getUsername().equals(username)) {
                        return users.get(i);
                    }
                }
            }
        }
        throw new UserNotFoundException(username);
    }

    public User generateUser(User user) throws UserAlreadyExistsException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return userRepository.save(user);
        } else {
            for (int i = 0; i < users.size(); i++) {
                if (i < users.size()) {
                    if (users.get(i).getUsername().equals(user.getUsername())) {
                        throw new UserAlreadyExistsException();
                    }
                }
            }
        }
        return userRepository.save(user);
    }

    public User amendUser(User user, String username) throws UserNotFoundException {
        User oldUser = retrieveUser(username);
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setUsername(user.getUsername());
        oldUser.setPassword(user.getPassword());
        userRepository.save(oldUser);
        return oldUser;
    }

    public void removeUser(String username) throws UserNotFoundException {
        User deletedUser = retrieveUser(username);
        userRepository.delete(deletedUser);
    }


}
