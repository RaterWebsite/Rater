package com.rater.application.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rater.application.model.Movie;
import com.rater.application.model.Rating;
import com.rater.application.model.User;
import com.rater.application.search.SearchRequestDTO;
import com.rater.application.service.MovieService;
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
        user.setRatings(new ArrayList<Rating>());
        service.saveUser(user);
        
    }

    @GetMapping("/getUserByUsername")
    @ResponseBody
    public User getUserByUsername(@RequestBody final String username) {
        System.out.println("Trying to find user with username: " + username);
        User result = service.getUserByUsername(username);
        return result;
    }

    @PostMapping("/addRating")
    public void addUserRating(@RequestBody Rating rating, @RequestHeader("username") String username) {
        User user = service.getUserByUsername(username);
        //user.getRatings().add(rating);
        List<Rating> ratings = new ArrayList<Rating>();
        ratings.add(rating);
        user.setRatings(ratings);
        user.setPassword("newpassword");
        service.saveUser(user);
    }


}
