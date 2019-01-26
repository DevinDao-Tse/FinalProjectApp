package com.example.a1530630.learningapplication.Models;

public class Module_Results
{
    public static String MODULE_RESULT_TABLE_NAME = "MODULE_RESULT";
    public static String MODULE_RESULT_COLUMN_MODULE_RES_ID = "ModuleResID";
    public static String MODULE_RESULT_COLUMN_MODULE_ID = "ModuleID";
    public static String MODULE_RESULT_COLUMN_USER_ID = "UserID";
    public static String MODULE_RESULT_COLUMN_LESSON_ONE = "Lesson1";
    public static String MODULE_RESULT_COLUMN_LESSON_TWO = "Lesson2";
    public static String MODULE_RESULT_COLUMN_LESSON_THREE = "Lesson3";
    public static String MODULE_RESULT_COLUMN_LESSON_FOUR = "Lesson4";
    public static String MODULE_RESULT_COLUMN_LESSON_FIVE = "Lesson5";

    public static String CREATE_MODULE_RESULT = "CREATE TABLE " + MODULE_RESULT_TABLE_NAME + " ("
            + MODULE_RESULT_COLUMN_MODULE_RES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MODULE_RESULT_COLUMN_MODULE_ID + " INTEGER ,"
            + MODULE_RESULT_COLUMN_USER_ID + " INTEGER REFERENCES " + User.COLUMN_ID+" ("+User.COLUMN_ID+"),"
            + MODULE_RESULT_COLUMN_LESSON_ONE + " INTEGER, "
            + MODULE_RESULT_COLUMN_LESSON_TWO + " INTEGER, "
            + MODULE_RESULT_COLUMN_LESSON_THREE + " INTEGER, "
            + MODULE_RESULT_COLUMN_LESSON_FOUR + " INTEGER, "
            + MODULE_RESULT_COLUMN_LESSON_FIVE + " INTEGER);";

    private Integer moduleresID, userID, moduleID,resultID, lesOne, lesTwo, lesThree, lesFour, lesFive;

    public Module_Results(){}

    public Module_Results(Integer usID)
    {

        this.resultID = null;
        this.userID = usID;
        this.moduleID = 0;
        this.lesOne = 0;
        this.lesTwo = 0;
        this.lesThree = 0;
        this.lesFour = 0;
        this.lesFive = 0;
    }

    public Integer getUserID() { return userID; }

    public void setUserID(Integer userID) { this.userID = userID; }

    public Integer getModuleID() { return moduleID; }

    public void setModuleID(Integer moduleID) { this.moduleID = moduleID; }

    public Integer getResultID() { return resultID; }

    public void setResultID(Integer resultID) { this.resultID = resultID; }

    public Integer getLesOne() { return lesOne; }

    public void setLesOne(Integer lesOne) { this.lesOne = lesOne; }

    public Integer getLesTwo() { return lesTwo; }

    public void setLesTwo(Integer lesTwo) { this.lesTwo = lesTwo; }

    public Integer getLesThree() { return lesThree; }

    public void setLesThree(Integer lesThree) { this.lesThree = lesThree; }

    public Integer getLesFour() { return lesFour; }

    public void setLesFour(Integer lesFour) { this.lesFour = lesFour; }

    public Integer getLesFive() { return lesFive; }

    public void setLesFive(Integer lesFive) { this.lesFive = lesFive; }
}
