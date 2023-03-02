package rater.application.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rater.application.model.Movie;
import rater.application.model.User;
import rater.application.search.SearchRequestDTO;
import rater.application.service.MovieService;
import rater.application.service.UserService;
import rater.application.service.helper.MovieDummyDataService;

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


}
