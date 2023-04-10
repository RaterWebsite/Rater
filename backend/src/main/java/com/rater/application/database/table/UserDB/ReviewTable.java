package com.rater.application.database.table.UserDB;

import com.rater.application.model.MovieRating;
import com.rater.application.model.Review;
import java.sql.Connection;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class ReviewTable {
    
    public static void addRecord(Review review, Connection dbConn) {
        PreparedStatement stmt = null;
        try {
            stmt = dbConn.prepareStatement("INSERT INTO reviews (username, reviewee, reviewText, score) values (?, ?, ?, ?)");
            stmt.setString(1, review.getReviewer());
            stmt.setString(2, review.getReviewee());
            stmt.setString(3, review.getText());
            stmt.setFloat(4, 1.0f);
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
            stmt = dbConn.prepareStatement("SELECT reviewText, score\n" 
                + "FROM reviews\n"
                + "WHERE reviews.username=? AND reviews.reviewee=?;");
            stmt.setString(1, review.getReviewer());
            stmt.setString(2, review.getReviewee());
            ResultSet rs = stmt.executeQuery();
            rs.next(); //get first row
            review.setRating(new HashMap<String, Float>());
            review.setText(rs.getString("reviewText"));
            return review;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        finally {
            try {
                if (stmt != null) {stmt.close(); }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void updateRecord(Review review, Connection dbConn) {
        PreparedStatement stmt = null;
        try {
            stmt = dbConn.prepareStatement("UPDATE reviews\n" 
                + "SET reviewText=?"
                + ", score=?\n"
                + "WHERE reviews.username=? AND reviews.reviewee=?;");
            stmt.setString(1, review.getText());
            stmt.setFloat(2, 1.0f);
            stmt.setString(3, review.getReviewer());
            stmt.setString(4, review.getReviewee());
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
}
