package com.example.a1530630.learningapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a1530630.learningapplication.Models.User;

public class SQLiteManage extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Android_Database";
    SharedPreferences sharedPreferences;

    public SQLiteManage(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    public User addNewUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(User.COLUMN_USERNAME, user.getUsername());
        values.put(User.COLUMN_PASSWORD,user.getPassword());
        values.put(User.COLUMN_FULL_NAME,user.getFullName());
        values.put(User.COLUMN_EMAIL,user.getEmail());
        //values.put(User.COLUMN_CREATED, user.getCreated());

        long id = db.insert(User.TABLE_NAME,null,values);
        user.setUserID((int)id);
        db.close();
        return user;
    }

    public boolean User_Exist(User user)
    {
        String sql = "SELECT * FROM "+User.TABLE_NAME+ " WHERE Email='" + user.getEmail()+ "' and Username='"+user.getUsername()+"'";
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor verify = db.rawQuery(sql,null);
        boolean check = false;
        if(verify.moveToFirst())
        {
            check = true;
        }
        else
            {
                check = false;
            }
            verify.close();
            db.close();
            return check;
    }

    public boolean Login(String username,String password)
    {
        String sql="SELECT * FROM "+User.TABLE_NAME+ " WHERE Username='"+username+ "' and Password='"+password+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor CheckUser = db.rawQuery(sql,null);
        if(CheckUser.moveToFirst())
        {
            return true;
        }
        else{
            return false;
        }
    }

    public User getUserInfo(User user)
    {
        if(Login(user.getUsername(),user.getPassword()))
        {
            String sql = "SELECT * FROM "+User.TABLE_NAME+" WHERE Username='"+ user.getUsername()+"' and Password='"+user.getPassword()+"'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor getInfo = db.rawQuery(sql,null);
            if(getInfo.moveToFirst())
            {
                user.setUserID(getInfo.getInt(getInfo.getColumnIndex(User.COLUMN_ID)));
                user.setUsername(getInfo.getString(getInfo.getColumnIndex(User.COLUMN_USERNAME)));
                user.setPassword(getInfo.getString(getInfo.getColumnIndex(User.COLUMN_PASSWORD)));
                user.setEmail(getInfo.getString(getInfo.getColumnIndex(User.COLUMN_EMAIL)));
                user.setFullName(getInfo.getString(getInfo.getColumnIndex(User.COLUMN_FULL_NAME)));
            }
            else
                {
                    user =null;
                }
            getInfo.close();
            db.close();
        }
        return user;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }
}

