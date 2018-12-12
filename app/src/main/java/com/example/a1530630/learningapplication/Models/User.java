package com.example.a1530630.learningapplication.Models;

public class User
{
    public static final String TABLE_NAME="Users";
    public static final String COLUMN_ID ="UserID";
    public static final String COLUMN_USERNAME="Username";
    public static final String COLUMN_PASSWORD="Password";
    public static final String COLUMN_FIRST_NAME="FirstName";
    public static final String COLUMN_LAST_NAME="LastName";
    public static final String COLUMN_EMAIL="Email";
    public static final String COLUMN_CREATED="Created";

    public static final String CREATE_TABLE="";

    private Integer userID;
    private String Username,Password,FirstName,LastName,Email,Created;

    public User(){}
    public User(String user,String pass, String first,String last, String email, String create)
    {
        this.userID = null;
        this.Username = user;
        this.Password = pass;
        this.FirstName =first;
        this.LastName = last;
        this.Email =email;
        this.Created =create;
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

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
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
