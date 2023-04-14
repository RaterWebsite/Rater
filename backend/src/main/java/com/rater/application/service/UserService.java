package com.rater.application.service;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rater.application.database.UserDatabase;
import com.rater.application.model.Movie;
import com.rater.application.model.Review;
import com.rater.application.model.User;
import com.rater.application.model.UserRelationship;
import com.rater.application.search.SearchRequestDTO;
import com.rater.application.search.util.SearchUtil;

@Service
public class UserService {

    private final UserDatabase userDB;
    private final MovieService movieService;
    private final RestHighLevelClient client;
    
    @Autowired
    public UserService(RestHighLevelClient client, MovieService movieService) {
        this.userDB = new UserDatabase();
        this.client = client;
        this.movieService = movieService;
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

    //TOOD: make reviewee interface/superclass and make this method generic for type reviewee
    public void createReview(Review review) {
        int numReviews = this.userDB.getReviewsByReviewee(review.getReviewee()).size(); //might want to cache number of records at some point
        //need to update the elastic search document with new review score (add it to running average)
        Movie current = movieService.getByTitle(review.getReviewee());
        Map<String, Float> reviewRating = review.getRating();
        for (Map.Entry<String, Float> category : current.getCategories().entrySet()) {
            Float userRating = reviewRating.get(category.getKey());
            Float currentRating = category.getValue();
            currentRating = currentRating * numReviews;
            currentRating += userRating;
            currentRating = currentRating / (numReviews+1); //get new average score of category
            category.setValue(currentRating);
        }
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
        return userDB.getReviewsByReviewee(reviewee);
    }

    
}

