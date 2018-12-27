package com.example.a1530630.learningapplication.Models;

public class User_Track
{
    public static String USER_TRACK_TABLE_NAME="USER_TRACK";
    public static String USER_TRACK_COLUMN_USERID = "UserID";
    public static String USER_TRACK_COLUMN_MODULEID = "ModuleID";
    public static String USER_TRACK_COLUMN_RESULT = "Results_Module";

    public static String CREATE_USER_TRACK_TABLE="CREATE TABLE "+USER_TRACK_TABLE_NAME+" ("
            +USER_TRACK_COLUMN_USERID+ " INTEGER, "
            +USER_TRACK_COLUMN_MODULEID+ " INTEGER, "
            +USER_TRACK_COLUMN_RESULT+ " INTEGER);";

    private Integer userID,moduleID,results;

    public User_Track(){}
    public User_Track(Integer uID, Integer mID,Integer res)
    {
        this.userID = uID;
        this.moduleID =mID;
        this.results =res;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getModuleID() {
        return moduleID;
    }

    public void setModuleID(Integer moduleID) {
        this.moduleID = moduleID;
    }

    public Integer getResults() {
        return results;
    }

    public void setResults(Integer results) {
        this.results = results;
    }
}
