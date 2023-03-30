package com.rater.application.database;

import java.sql.Connection;  
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Value;

import com.rater.application.database.table.UserDB.ReviewTable;
import com.rater.application.database.table.UserDB.UserTable;
import com.rater.application.model.Review;
import com.rater.application.model.User;

public class UserDatabase implements ApplicationDatabase {

    private Connection dbConn;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPass;

    public UserDatabase() {

    }
    
    public void connectToDB() {
        String DB_URL = "jdbc:mysql://localhost/USERS";
        String USER = "root";
        String pass = "pass";
        try {
            this.dbConn = DriverManager.getConnection(DB_URL, USER, pass);
            String sql = "CREATE DATABASE USERS";
            Statement stmt = dbConn.createStatement();
            //stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
    }

    public void createTables() {
        try {
            String createUserTable = "CREATE TABLE IF NOT EXISTS users (\n"  
                + " username varchar(40) PRIMARY KEY,\n"  
                + " password text NOT NULL\n"  
                + ");";
            Statement stmt = this.dbConn.createStatement();
            stmt.execute(createUserTable);
            String createRatingTable = "CREATE TABLE IF NOT EXISTS reviews (\n"  
                + " username varchar(40) NOT NULL,\n"  
                + " reviewee varchar(60) NOT NULL,\n" 
                + " reviewText text,\n" 
                + " score float NOT NULL,\n"  
                + " CONSTRAINT user_review PRIMARY KEY (username, reviewee),\n"
                + " FOREIGN KEY (username) REFERENCES users(username)\n" 
                + ");";
            stmt.execute(createRatingTable);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
    }

    public void addRecord(Object record) {
        if (record instanceof User) {
            User user = (User)record;
            UserTable.addRecord(user, dbConn);
        } else if (record instanceof Review) {
            Review review = (Review)record;
            ReviewTable.addRecord(review, dbConn);
        } else {
            System.out.println("Cannot add invalid object. Needed type: User OR type: Review.\n");
        }
    }

    public Object getRecord(Object object) {
        if (object instanceof User) {
            User user = (User)object;
            return UserTable.getRecord(user, dbConn);
        } else if (object instanceof Review) {
            Review review = (Review)object;
            return ReviewTable.getRecord(review, dbConn);
        } else {
            System.out.println("Cannot add invalid object. Needed type: User OR type: Review.\n");
            return null;
        }
    }

    public void updateRecord(Object record) {
        if (record instanceof User) {
            User user = (User)record;
            UserTable.updateRecord(user, dbConn);
        } else if (record instanceof Review) {
            Review review = (Review)record;
            ReviewTable.updateRecord(review, dbConn);
        } else {
            System.out.println("Cannot update invalid object. Needed type: User OR type: Review.\n");
        }
    }

}