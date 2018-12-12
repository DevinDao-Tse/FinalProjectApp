package com.example.a1530630.learningapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a1530630.learningapplication.Models.User;

public class SQLiteManage extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Android_Database";


    public SQLiteManage(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    public void addNewUser(User user)
    {

    }

    public boolean Login(User user)
    {
        return false;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_TABLE); //table for user

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}

