package com.rater.application.database.table.UserDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.rater.application.model.User;
import com.rater.application.model.UserRelationship;

public class FriendsTable {

    public static void addRecord(UserRelationship relationship, Connection dbConn) {
        PreparedStatement stmt = null;
        try {
            stmt = dbConn.prepareStatement("INSERT INTO friends (relatingUser, relatedUser, relatingUserStatus, relatedUserStatus) values (?, ?, ?, ?)");
            stmt.setString(1, relationship.getRelatingUser());
            stmt.setString(2, relationship.getRelatedUser());
            stmt.setString(3, relationship.getRelatingUserStatus());
            stmt.setString(4, relationship.getRelatedUserStatus());
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

    public static Object getRecord(UserRelationship relationship, Connection dbConn) {
        PreparedStatement stmt = null;
        try {
            stmt = dbConn.prepareStatement("SELECT relatingUser, relatedUser, relatingUserStatus, relatedUserStatus\n" 
                + "FROM friends\n"
                + "WHERE (relatingUser = ? AND relatedUser = ?) OR (relatingUser = ? AND relatedUser = ?);");
            /* A user relationship will only be stored once, have to check which user is the 'relating' one */
            stmt.setString(1, relationship.getRelatingUser());
            stmt.setString(2, relationship.getRelatedUser());
            stmt.setString(3, relationship.getRelatedUser());
            stmt.setString(4, relationship.getRelatingUser());

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                //no relationship is established right now
                return null;
            }
            relationship.setRelatedUser(rs.getString("relatedUser"));
            relationship.setRelatingUser(rs.getString("relatingUser"));
            relationship.setRelatingUserStatus(rs.getString("relatingUserStatus"));
            relationship.setRelatedUserStatus(rs.getString("relatedUserStatus"));
            return relationship;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            try {
                if (stmt != null) { stmt.close(); }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
