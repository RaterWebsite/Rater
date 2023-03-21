package com.rater.application.model;

public class Review {
    private float rating;
    private String text;

    
    public Review() {
    }
    public Review(float rating, String text) {
        this.rating = rating;
        this.text = text;
    }
    public float getRating() {
        return rating;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    
}
