package com.example.a1530630.learningapplication.Models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class User
{
    public static final String TABLE_NAME="Users";
    public static final String COLUMN_ID ="UserID";
    public static final String COLUMN_USERNAME="Username";
    public static final String COLUMN_PASSWORD="Password";
    public static final String COLUMN_FULL_NAME="FullName";
    public static final String COLUMN_EMAIL="Email";
    public static final String COLUMN_CREATED="Created";

    public static final String CREATE_TABLE=" CREATE TABLE "+TABLE_NAME+" ("
            + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + "TEXT,"
            + COLUMN_PASSWORD + "TEXT,"
            + COLUMN_FULL_NAME + "TEXT,"
            + COLUMN_EMAIL + "TEXT,"
            + COLUMN_CREATED + "TEXT,"
            + COLUMN_USERNAME + "TEXT);";

    private Integer userID;
    private String Username,Password,FullName,Email,Created;

    public User(){}
    public User(String user,String pass, String full, String email)
    {
        this.userID = null;
        this.Username = user;
        this.Password = pass;
        this.FullName =full;
        this.Email =email;
        this.Created =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }
}
