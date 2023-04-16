package com.rater.application.database.table.UserDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.rater.application.model.Review;

public class MovieRatingsTable {
    

    public static void addRecord(Review review, Connection dbConn) {

        PreparedStatement stmt = null;
        try {
            stmt = dbConn.prepareStatement("INSERT INTO movie_ratings values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, review.getReviewer());
            stmt.setString(2, review.getReviewee());
            Map<String, Float> rating = review.getRating();
            stmt.setFloat(3, rating.get("plot"));
            stmt.setFloat(4, rating.get("acting"));
            stmt.setFloat(5, rating.get("ending"));
            stmt.setFloat(6, rating.get("soundtrack"));
            stmt.setFloat(7, rating.get("cinematography"));
            stmt.setFloat(8, rating.get("familyFriendly"));
            stmt.setFloat(9, rating.get("funny"));
            stmt.setFloat(10, rating.get("action"));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (stmt != null) { stmt.close(); }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    public static Review getRecord(Review review, Connection dbConn) {
        PreparedStatement stmt = null;
        try {
            stmt = dbConn.prepareStatement("SELECT plot, acting, ending, soundtrack, cinematography, family_friendly, funny, action\n"
                + "FROM movie_ratings\n"
                + "WHERE movie_ratings.reviewer=? AND movie_ratings.reviewee=?");
            stmt.setString(1, review.getReviewer());
            stmt.setString(2, review.getReviewee());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            Map<String, Float> ratings = new HashMap<>();
            ratings.put("plot", rs.getFloat("plot"));
            ratings.put("acting", rs.getFloat("acting"));
            ratings.put("ending", rs.getFloat("ending"));
            ratings.put("soundtrack", rs.getFloat("soundtrack"));
            ratings.put("cinematography", rs.getFloat("cinematography"));
            ratings.put("familyFriendly", rs.getFloat("family_friendly"));
            ratings.put("funny", rs.getFloat("funny")); 
            ratings.put("action", rs.getFloat("action"));

            review.setRating(ratings);
            return review;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }


}
