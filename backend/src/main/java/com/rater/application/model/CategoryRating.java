package com.rater.application.model;

import java.util.Map;

public interface CategoryRating {

    void setCategoryRatings(Map<String, Float> ratings);

    Map<String, Float> getCategoryRatings();
}
