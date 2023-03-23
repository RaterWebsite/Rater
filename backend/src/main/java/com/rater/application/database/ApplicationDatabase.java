package com.rater.application.database;

public interface ApplicationDatabase {
    
    public void connectToDB();
    public void createTables();
    public void addRecord(Object record);
    public Object getRecord(Object record);
    public void updateRecord(Object record);
}
