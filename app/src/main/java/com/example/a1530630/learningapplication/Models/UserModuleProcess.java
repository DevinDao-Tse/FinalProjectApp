package com.example.a1530630.learningapplication.Models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserModuleProcess
{
    public static final String UM_TABLE_NAME="UserProcess";
    public static final String UM_COLUMN_ID ="UserProID";
    public static final String UM_COLUMN_USER_ID ="UserID";
    public static final String UM_COLUMN_USERNAME="Username";
    public static final String UM_COLUMN_PASSWORD="Password";
    public static final String UM_COLUMN_FULL_NAME="FullName";
    public static final String UM_COLUMN_EMAIL="Email";
    public static final String UM_COLUMN_CREATED="Created";
    public static String UM_COLUMN_MODULE_ID = "ModuleID";
    public static String UM_COLUMN_LESSON_ONE = "Lesson1";
    public static String UM_COLUMN_LESSON_TWO = "Lesson2";
    public static String UM_COLUMN_LESSON_THREE = "Lesson3";
    public static String UM_COLUMN_LESSON_FOUR = "Lesson4";
    public static String UM_COLUMN_LESSON_FIVE = "Lesson5";

    public static final String CREATE_UM_TABLE=" CREATE TABLE "+UM_TABLE_NAME+" ("
            + UM_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +UM_COLUMN_USER_ID + " INTEGER REFERENCES " +User.COLUMN_ID+"("+User.COLUMN_ID+"),"
            + UM_COLUMN_USERNAME + " TEXT, "
            + UM_COLUMN_PASSWORD + " TEXT, "
            + UM_COLUMN_FULL_NAME + " TEXT, "
            + UM_COLUMN_EMAIL + " TEXT, "
            + UM_COLUMN_CREATED + " TEXT,"
            + UM_COLUMN_MODULE_ID + " INTEGER REFERENCES " +Modules.MODULE_COLUMN_ID+"("+Modules.MODULE_COLUMN_ID+"),"
            + UM_COLUMN_LESSON_ONE + " REAL, "
            + UM_COLUMN_LESSON_TWO + " REAL, "
            + UM_COLUMN_LESSON_THREE + " REAL, "
            + UM_COLUMN_LESSON_FOUR + " REAL, "
            + UM_COLUMN_LESSON_FIVE + " REAL);";


    private Integer userProID, userID, moduleID, lessonOne, lessonTwo,lessonThree,lessonFour,lessonFive;
    private String userN, passW, fullN, emailAddress, dateCompleted;
    private String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    public UserModuleProcess(){}

    public UserModuleProcess(int uID, int modID, String u, String pw, String fn, String em)
    {
        this.userProID = null;
        this.userID = uID;
        this.userN = u;
        this.passW = pw;
        this.fullN = fn;
        this.emailAddress = em;
        this.moduleID = modID;
        this.lessonOne = 0;
        this.lessonTwo = 0;
        this.lessonThree = 0;
        this.lessonFour = 0;
        this.lessonFive = 0;
        this.dateCompleted = date;

    }


    public Integer getUserProID() {
        return userProID;
    }

    public void setUserProID(Integer userID) {
        this.userID = userProID;
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

    public Integer getLessonOne() {
        return lessonOne;
    }

    public void setLessonOne(Integer lessonOne) {
        this.lessonOne = lessonOne;
    }

    public Integer getLessonTwo() {
        return lessonTwo;
    }

    public void setLessonTwo(Integer lessonTwo) {
        this.lessonTwo = lessonTwo;
    }

    public Integer getLessonThree() {
        return lessonThree;
    }

    public void setLessonThree(Integer lessonThree) {
        this.lessonThree = lessonThree;
    }

    public Integer getLessonFour() {
        return lessonFour;
    }

    public void setLessonFour(Integer lessonFour) {
        this.lessonFour = lessonFour;
    }

    public Integer getLessonFive() {
        return lessonFive;
    }

    public void setLessonFive(Integer lessonFive) {
        this.lessonFive = lessonFive;
    }

    public String getUserN() {
        return userN;
    }

    public void setUserN(String userN) {
        this.userN = userN;
    }

    public String getPassW() {
        return passW;
    }

    public void setPassW(String passW) {
        this.passW = passW;
    }

    public String getFullN() {
        return fullN;
    }

    public void setFullN(String fullN) {
        this.fullN = fullN;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

}
