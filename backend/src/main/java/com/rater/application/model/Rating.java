package com.rater.application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Rating {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Long id;

    @Column(name = "movieName")
    private String movieName;
    @Column(name = "score")
    private float score;
    @Column(name = "review")
    private String review;

    public Rating(String movieName, float score, String review) {
        this.movieName = movieName;
        this.score = score;
        this.review = review;
    }

    public Rating() {
        
    }

    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public float getScore() {
        return score;
    }
    public void setScore(float score) {
        this.score = score;
    }
    public String getReview() {
        return review;
    }
    public void setReview(String review) {
        this.review = review;
    }

    
    
}
