package rater.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rater.application.model.User;
import rater.application.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;
    
    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void createUser(User user) {
        repository.save(user);
    }
}
