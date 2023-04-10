package com.rater.application.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;  
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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

}
