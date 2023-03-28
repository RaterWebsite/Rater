package com.rater.application.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rater.application.database.UserDatabase;
import com.rater.application.model.Review;
import com.rater.application.model.User;

@Service
public class UserService {

    private final UserDatabase userDB;
    
    @Autowired
    public UserService() {
        this.userDB = new UserDatabase();
        //userDB.connectToDB();
        //userDB.createTables();
        
    }

    public void createUser(User user) {
        this.userDB.addRecord(user);
    }

    public User getUserByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        return (User)this.userDB.getRecord(user);
    }

    public void createReview(Review review) {
        this.userDB.addRecord(review);
    }

    public Review getReview(Review review) {
        return (Review)this.userDB.getRecord(review);
    }

    public void updateReview(Review review) {
        this.userDB.updateRecord(review);
    }
}
