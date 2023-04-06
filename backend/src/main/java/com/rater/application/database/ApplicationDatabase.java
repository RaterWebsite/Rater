package com.rater.application.database;

public interface ApplicationDatabase {
    
    void connectToDB();
    void createTables();
    void addRecord(Object record);
    Object getRecord(Object record);
    void updateRecord(Object record);
}
