package com.rater.application.model;

import java.util.Map;

public class MovieRating implements CategoryRating {

    private Map<String, Float> ratings;
    
    public void setCategoryRatings(Map<String, Float> ratings) {
        this.ratings = ratings;
    }

    public Map<String, Float> getCategoryRatings() {
        return this.ratings;
    }


}
