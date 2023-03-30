package com.rater.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rater.application.model.Review;
import com.rater.application.model.User;
import com.rater.application.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }
    
    @PostMapping ("/createUser")
    public void createUser(@RequestBody final User user) {
        service.createUser(user);
        
    }

    @GetMapping("/getUserByUsername")
    @ResponseBody
    public User getUserByUsername(@RequestBody final String username) {
        System.out.println("Trying to find user with username: " + username);
        User result = service.getUserByUsername(username);
        return result;
    }

    @PostMapping("/addReview")
    public void addUserRating(@RequestBody Review review) {
        service.createReview(review);
    }

    @GetMapping("/getReview")
    public Review getReview(@RequestBody Review review) {
        return service.getReview(review);
    }

    @PostMapping("/updateReview")
    public void updateReview(@RequestBody Review review) {
        service.updateReview(review);
    }

    @GetMapping("/getDBInfo")
    public String getDBInfo() {
        return service.getDBInfo();
    }


}