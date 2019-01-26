package com.example.a1530630.learningapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a1530630.learningapplication.Models.Module_Results;
import com.example.a1530630.learningapplication.Models.Modules;
import com.example.a1530630.learningapplication.Models.User;
import com.example.a1530630.learningapplication.Models.User_Track;

public class SQLiteManage extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Android_Database2";
    SharedPreferences sharedPreferences;

    public SQLiteManage(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    //add new user to database
    public User addNewUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(User.COLUMN_USERNAME, user.getUsername());
        values.put(User.COLUMN_PASSWORD,user.getPassword());
        values.put(User.COLUMN_FULL_NAME,user.getFullName());
        values.put(User.COLUMN_EMAIL,user.getEmail());
        values.put(User.COLUMN_CREATED, user.getCreated());

        long id = db.insert(User.USER_TABLE_NAME,null,values);
        user.setUserID((int)id);
        db.close();
        return user;
    }

    public Module_Results createResult(Module_Results mod,int modNum)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Module_Results.MODULE_RESULT_COLUMN_USER_ID,mod.getUserID());
        values.put(Module_Results.MODULE_RESULT_COLUMN_MODULE_ID,modNum);
        long id = db.insert(Module_Results.MODULE_RESULT_TABLE_NAME,null,values);
        mod.setResultID((int)id);
        db.close();
        return mod;
    }

    public Boolean setModule(int modNum, int user)
    {
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT "+Module_Results.MODULE_RESULT_COLUMN_MODULE_ID+" FROM "+Module_Results.MODULE_RESULT_TABLE_NAME+" WHERE "
                +Module_Results.MODULE_RESULT_COLUMN_MODULE_ID+ " = "+modNum + " AND "+ Module_Results.MODULE_RESULT_COLUMN_USER_ID+" = "+user;

        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.moveToFirst()) { check = true; }
        else { check = false; }

        return check;
    }

    public Cursor getModuleResID(int userID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT "+Module_Results.MODULE_RESULT_COLUMN_MODULE_RES_ID+" FROM "+Module_Results.MODULE_RESULT_TABLE_NAME+ " WHERE "+ Module_Results.MODULE_RESULT_COLUMN_USER_ID+ " = "+userID;
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }

    public boolean testset(long rowid ,int test,String lesson)
    {
        String idConvert = String.valueOf(rowid);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(lesson, test);
        db.update(Module_Results.MODULE_RESULT_TABLE_NAME, values, Module_Results.MODULE_RESULT_COLUMN_MODULE_RES_ID + " =  ?",new String[]{idConvert});

        return true;
    }
    //verify if a user exist through email,full name
    public boolean User_Exist(String email,String username)
    {
        String sql = "SELECT * FROM "+User.USER_TABLE_NAME+ " WHERE Email='" + email+ "' or Username='"+username+"'";
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor verify = db.rawQuery(sql,null);
        boolean check = false;

        if(verify.moveToFirst())
        {
            check = true;
        }
        else {
            check = false;
        }
            return check;
    }
    //Verify Login with username and password
    public boolean Login(String username,String password)
    {
        String sql="SELECT * FROM "+User.USER_TABLE_NAME+ " WHERE Username='"+username+ "' and Password='"+password+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor CheckUser = db.rawQuery(sql,null);

        if(CheckUser.moveToFirst()) { return true; }
        else{ return false; }
    }

    public User getUserInfo(User user)
    {
        if(Login(user.getUsername(),user.getPassword()))
        {
            String sql = "SELECT * FROM "+User.USER_TABLE_NAME+" WHERE Username='"+ user.getUsername()+"' and Password='"+user.getPassword()+"'";
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
            else { user =null; }
            getInfo.close();
            db.close();
        }
        return user;
    }

    public User UpdateProfile(User user, int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newInfo = new ContentValues();

        newInfo.put(User.COLUMN_USERNAME, user.getUsername());
        newInfo.put(User.COLUMN_PASSWORD,user.getPassword());
        newInfo.put(User.COLUMN_FULL_NAME,user.getFullName());
        newInfo.put(User.COLUMN_EMAIL,user.getEmail());

        int i = db.update(User.USER_TABLE_NAME, newInfo, "UserID = "+ id,null);
        db.close();
        return user;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_TABLE);
        //db.execSQL(Modules.CREATE_MODULE_TABLE);
        db.execSQL(Module_Results.CREATE_MODULE_RESULT);
        //db.execSQL(User_Track.CREATE_USER_TRACK_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}

