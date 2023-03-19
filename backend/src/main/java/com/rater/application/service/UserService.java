package com.rater.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rater.application.model.User;
import com.rater.application.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;
    
    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void saveUser(User user) {
        repository.save(user);
    }

    public User getUserByUsername(String username) {
        Optional<User> user = repository.findById(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            System.out.println("Couldn't find user with username: " + username);
            return null;
        }
    }
}
