package com.rater.application.model;

public class UserRelationship {
    
    private String relatingUser;
    private String relatedUser;
    private String relatingUserStatus;
    private String relatedUserStatus;

    public UserRelationship(String relatingUser, String relatedUser) {
        this.relatingUser = relatingUser;
        this.relatedUser = relatedUser;
    }

    public String getRelatingUser() {
        return this.relatingUser;
    }

    public void setRelatingUser(String user) {
        this.relatingUser = user;
    }

    public String getRelatedUser() {
        return this.relatedUser;
    }

    public void setRelatedUser(String user) {
        this.relatedUser = user;
    }

    public String getRelatingUserStatus() {
        return relatingUserStatus;
    }

    public void setRelatingUserStatus(String relatingUserStatus) {
        this.relatingUserStatus = relatingUserStatus;
    }

    public String getRelatedUserStatus() {
        return relatedUserStatus;
    }

    public void setRelatedUserStatus(String relatedUserStatus) {
        this.relatedUserStatus = relatedUserStatus;
    }

    
}
