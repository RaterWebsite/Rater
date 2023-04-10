package com.rater.application.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;  
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;

import com.rater.application.database.table.UserDB.FriendsTable;
import com.rater.application.database.table.UserDB.ReviewTable;
import com.rater.application.database.table.UserDB.UserTable;
import com.rater.application.helper.Util;
import com.rater.application.model.Review;
import com.rater.application.model.User;
import com.rater.application.model.UserRelationship;

public class UserDatabase implements ApplicationDatabase {

    public Connection dbConn;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPass;

    public UserDatabase() {

    }
    
    public void connectToDB() {
        String username = "root";
        String password = "pass";
        //String dockerUrl = "${spring.user_datasource.url}";
        String dbUrl = "jdbc:mysql://db:3306/USERS";
        try {
            this.dbConn = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("HERE IS THE METADATA FOR THE DATABASE");
            System.out.println(dbConn.getMetaData());
        } catch (SQLException e) {
            System.out.println(e);
        }
        
    }

    public void insertDBDummyData() {
        try {
            Scanner scan = new Scanner(new File("/app/documents/database/Populate.txt"));
            scan.useDelimiter(";");
            Statement stmt = this.dbConn.createStatement();
            while (scan.hasNext()) {
                stmt.execute(scan.next());
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    public void createTables() {
        try {
            Scanner scan = new Scanner(new File("/app/documents/database/Create.txt"));
            scan.useDelimiter(";");
            Statement stmt = this.dbConn.createStatement();
            while (scan.hasNext()) {
                stmt.execute(scan.next());
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        
    }

    public void addRecord(Object record) {
        if (record instanceof User) {
            User user = (User) record;
            UserTable.addRecord(user, dbConn);
        } else if (record instanceof Review) {
            Review review = (Review) record;
            ReviewTable.addRecord(review, dbConn);
        } else {
            System.out.println("Cannot add invalid object. Needed type: User OR type: Review.\n");
        }
    }

    public Object getRecord(Object object) {
        if (object instanceof User) {
            User user = (User) object;
            return UserTable.getRecord(user, dbConn);
        } else if (object instanceof Review) {
            Review review = (Review) object;
            return ReviewTable.getRecord(review, dbConn);
        } else if (object instanceof UserRelationship) {
            UserRelationship relationship = (UserRelationship) object;
            return FriendsTable.getRecord(relationship, dbConn);
        } else {
            System.out.println("Cannot add invalid object. Needed type: User OR type: Review.\n");
            return null;
        }
    }

    public void updateRecord(Object record) {
        if (record instanceof User) {
            User user = (User) record;
            UserTable.updateRecord(user, dbConn);
        } else if (record instanceof Review) {
            Review review = (Review) record;
            ReviewTable.updateRecord(review, dbConn);
        } else {
            System.out.println("Cannot update invalid object. Needed type: User OR type: Review.\n");
        }
    }

    public List<Review> getReviewsByReviewee(String reviewee) {
        try {
            List<Review> reviews = new ArrayList<>();
            Scanner scan = new Scanner(new File("/app/documents/database/movie_ratingsQueries.txt"));
            scan.useDelimiter(";");
            String sql = scan.next();
            PreparedStatement stmt = dbConn.prepareStatement(sql);
            stmt.setString(1, reviewee);
            ResultSet rs = stmt.executeQuery();
            String reviewer;
            Map<String, Float> ratings = new HashMap<String, Float>();

            while (rs.next()) {
                reviewer = rs.getString("reviewer");
                ratings.put("plot", rs.getFloat("plot"));
                ratings.put("acting", rs.getFloat("acting"));
                ratings.put("ending", rs.getFloat("ending"));
                ratings.put("soundtrack", rs.getFloat("soundtrack"));
                ratings.put("cinematography", rs.getFloat("cinematography"));
                ratings.put("family_friendly", rs.getFloat("family_friendly"));
                ratings.put("funny", rs.getFloat("funny")); 
                ratings.put("action", rs.getFloat("action"));
                Review review = new Review();
                review.setReviewer(reviewer);
                review.setReviewee(reviewee);
                review = (Review) getRecord(review);    
                review.setRating(ratings);

                reviews.add(review);
                ratings.clear();           
            }
            return reviews;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
        
        
    }

}
