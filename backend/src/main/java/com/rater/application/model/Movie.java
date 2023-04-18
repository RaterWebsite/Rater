package com.rater.application.model;

import java.util.List;
import java.util.Map;

public class Movie {

    private String id;
    private String title;
    private List<String> starring;
    private int runtime; //minutes
    private List<String> genre;
    private String description;
    private int releaseYear;
    private String mpaaRating; //G, PG, etc.
    private String imageUrl;
    private Map<String, Float> categories;
    private List<Review> reviews;
    private List<String> recommendations;

    
    public List<String> getStarring() {
        return starring;
    }
    public void setStarring(List<String> starring) {
        this.starring = starring;
    }
    public int getRuntime() {
        return runtime;
    }
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }
    public List<String> getGenre() {
        return genre;
    }
    public void setGenre(List<String> genre) {
        this.genre = genre;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getReleaseYear() {
        return releaseYear;
    }
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    public String getMpaaRating() {
        return mpaaRating;
    }
    public void setMpaaRating(String rating) {
        this.mpaaRating = rating;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String image) {
        this.imageUrl = image;
    }
    public Map<String, Float> getCategories() {
        return categories;
    }
    public void setCategories(Map<String, Float> categories) {
        this.categories = categories;
    }
    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<Review> getReviews() {
        return reviews;
    }
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    public List<String> getRecommendations() {
        return recommendations;
    }
    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    
    
    
}
