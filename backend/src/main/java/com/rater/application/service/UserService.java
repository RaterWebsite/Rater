package com.rater.application.service;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rater.application.database.UserDatabase;
import com.rater.application.model.Review;
import com.rater.application.model.User;
import com.rater.application.model.UserRelationship;

@Service
public class UserService {

    private final UserDatabase userDB;
    
    @Autowired
    public UserService() {
        this.userDB = new UserDatabase();
    }

    public String connectToDB() {
        try {
            this.userDB.connectToDB();
            this.userDB.createTables();
            return "Database metadata: \n" + this.userDB.dbConn.getMetaData();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        
    }

    public void insertDBDummyData() {
        userDB.insertDBDummyData();
    }

    public void createUser(User user) {
        this.userDB.addRecord(user);
    }

    public User getUserByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        return (User) this.userDB.getRecord(user);
    }

    public void createReview(Review review) {
        this.userDB.addRecord(review);
    }

    public Review getReview(Review review) {
        return (Review) this.userDB.getRecord(review);
    }

    public void updateReview(Review review) {
        this.userDB.updateRecord(review);
    }

    //TODO: want logic to prevent user from following if they already are (give them a message or something (need to communictE WITH webiste for this))
    public void followUser(String follower, String followed) {
        UserRelationship relationship = new UserRelationship(follower, followed);
        UserRelationship returned = (UserRelationship) this.userDB.getRecord(relationship);
        if (returned == null) {
            //not in database
            relationship.setRelatingUserStatus("follows");
            this.userDB.addRecord(relationship);
        } else {
            //was in database, take look
            if (follower.equals(returned.getRelatingUser())) {
                returned.setRelatingUserStatus("follows");
            } else {
                returned.setRelatedUserStatus("follows");
            }
            this.userDB.addRecord(returned);
        }
        
    }

    public List<Review> getReviewsByReviewee(String reviewee) {
        return new ArrayList<>();
    }

    
}

