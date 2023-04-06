package com.rater.application.database.table.UserDB;

import com.rater.application.model.User;
import java.sql.Connection;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserTable {

    public static void addRecord(User user, Connection dbConn) {
        PreparedStatement stmt = null;
        try {
            stmt = dbConn.prepareStatement("INSERT INTO users (username, password) values (?, ?)");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        finally {
            try {
                if (stmt != null) {stmt.close(); }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    public static User getRecord(User user, Connection dbConn) {
        PreparedStatement stmt = null;
        try {
            stmt = dbConn.prepareStatement("SELECT password\n" 
                + "FROM users\n"
                + "WHERE users.username=?;");
            stmt.setString(1, user.getUsername());
            ResultSet rs = stmt.executeQuery();
            rs.next(); //get first row
            user.setPassword(rs.getString("password"));
            return user;
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

    public static void updateRecord(User user, Connection dbConn) {
        
    }
}