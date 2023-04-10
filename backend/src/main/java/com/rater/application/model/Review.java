package com.rater.application.model;

import java.util.Map;

public class Review {
    private Map<String, Float> rating;
    private String text;
    private String reviewer;
    private String reviewee;

    
    public Review() {
    }
    public Review(Map<String, Float> rating, String text) {
        this.rating = rating;
        this.text = text;
    }
    public Map<String, Float> getRating() {
        return rating;
    }
    public void setRating(Map<String, Float> rating) {
        this.rating = rating;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getReviewer() {
        return reviewer;
    }
    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }
    public String getReviewee() {
        return reviewee;
    }
    public void setReviewee(String reviewee) {
        this.reviewee = reviewee;
    }
    

    
}
