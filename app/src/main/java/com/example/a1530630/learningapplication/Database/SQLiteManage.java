package com.example.a1530630.learningapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;

import com.example.a1530630.learningapplication.Models.AudioAndImages;
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

    public AudioAndImages createRowAud(AudioAndImages files)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(AudioAndImages.AudandImg_MODULE,files.getModuleNum());
        values.put(AudioAndImages.AudandImg_LESSON_COLUMN,files.getLessonNum());

        values.put(AudioAndImages.AudandImg_IMAGE_COLUMN,files.getByteImg());
        values.put(AudioAndImages.AudandImg_AUDIO_COLUMN,files.getByteAud());

        long id = db.insert(AudioAndImages.AudandImg_TABLE_NAME, null,values);
        files.setFileID((int)id);
        db.close();
        return files;
    }

    public Modules createModules(Modules mod)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Modules.MODULE_COLUMN_NUMBER,mod.getModuleNum());
        long id = db.insert(Modules.MODULE_TABLE_NAME,null,values);
        mod.setModuleID((int)id);
        db.close();

        return mod;
    }

    //adds latest one
    public Cursor createNewModule()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT "+Modules.MODULE_COLUMN_NUMBER+ " FROM "+ Modules.MODULE_TABLE_NAME+ " ORDER BY "+Modules.MODULE_COLUMN_NUMBER+ " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }

    public Cursor ReadModule()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT "+Modules.MODULE_COLUMN_NUMBER+ " FROM "+ Modules.MODULE_TABLE_NAME;
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
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
    public User_Track createTrack(User_Track user_track, int modNum)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(User_Track.USER_TRACK_COLUMN_USERID, user_track.getUserID());
        values.put(User_Track.USER_TRACK_COLUMN_MODULEID, modNum);
        db.insert(User_Track.USER_TRACK_TABLE_NAME,null,values);
        db.close();
        return user_track;
    }


    public Float TrackProfile(int UserID,int Module)
    {
        float l1=0;     float l2=0;     float l3=0;     float l4=0;     float l5=0;
        float Total =0;
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT "+ Module_Results.MODULE_RESULT_COLUMN_LESSON_ONE+", "
                +Module_Results.MODULE_RESULT_COLUMN_LESSON_TWO+", "
                +Module_Results.MODULE_RESULT_COLUMN_LESSON_THREE+", "
                +Module_Results.MODULE_RESULT_COLUMN_LESSON_FOUR+", "
                +Module_Results.MODULE_RESULT_COLUMN_LESSON_FIVE+" FROM " + Module_Results.MODULE_RESULT_TABLE_NAME+ " WHERE "
                +Module_Results.MODULE_RESULT_COLUMN_MODULE_ID+ " = "+Module+ " AND "+ Module_Results.MODULE_RESULT_COLUMN_USER_ID+ " = "+UserID;

        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.moveToFirst()){

            l1= cursor.getFloat(cursor.getColumnIndex(Module_Results.MODULE_RESULT_COLUMN_LESSON_ONE));
            l2= cursor.getFloat(cursor.getColumnIndex(Module_Results.MODULE_RESULT_COLUMN_LESSON_TWO));
            l3= cursor.getFloat(cursor.getColumnIndex(Module_Results.MODULE_RESULT_COLUMN_LESSON_THREE));
            l4= cursor.getFloat(cursor.getColumnIndex(Module_Results.MODULE_RESULT_COLUMN_LESSON_FOUR));
            l5= cursor.getFloat(cursor.getColumnIndex(Module_Results.MODULE_RESULT_COLUMN_LESSON_FIVE));
            Total =(l1+l2+l3+l4+l5) /5;

        }
        return Total;
    }
    public boolean updateTrack(int UserID, int Module)
    {
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(User_Track.USER_TRACK_COLUMN_RESULT,TrackProfile(UserID,Module));
        String userCon = String.valueOf(UserID);
        String modCon = String.valueOf(Module);
        db.update(User_Track.USER_TRACK_TABLE_NAME,value, User_Track.USER_TRACK_COLUMN_USERID +
                " = ? AND " + User_Track.USER_TRACK_COLUMN_MODULEID +" = ?",new String[]{userCon,modCon});
        return true;
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

    public boolean setTrack(int modNum, int user)
    {
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT "+User_Track.USER_TRACK_COLUMN_MODULEID+" FROM "+User_Track.USER_TRACK_TABLE_NAME+" WHERE "
                +User_Track.USER_TRACK_COLUMN_MODULEID+ " = "+modNum + " AND "+ User_Track.USER_TRACK_COLUMN_USERID+" = "+user;
        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.moveToFirst()) { check = true; }
        else { check = false; }

        return check;
    }

    public Cursor getModuleResID(int userID, int mod)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT "+Module_Results.MODULE_RESULT_COLUMN_MODULE_RES_ID+" FROM "+Module_Results.MODULE_RESULT_TABLE_NAME+ " WHERE "
                + Module_Results.MODULE_RESULT_COLUMN_USER_ID+ " = "+userID +" AND "+ Module_Results.MODULE_RESULT_COLUMN_MODULE_ID +" = "+mod;
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }

    public boolean TestSet(long rowID ,float test,String lesson, int Mod)
    {
        String idConvert = String.valueOf(rowID);
        String modConvert = String.valueOf(Mod);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(lesson, test);
        db.update(Module_Results.MODULE_RESULT_TABLE_NAME, values, Module_Results.MODULE_RESULT_COLUMN_MODULE_RES_ID + " =  ? AND "
                +Module_Results.MODULE_RESULT_COLUMN_MODULE_ID + " = ?",new String[]{idConvert, modConvert});
        return true;
    }
    //verify if a user exist through email,full name
    public boolean User_Exist(String email,String username)
    {
        String sql = "SELECT * FROM "+User.USER_TABLE_NAME+ " WHERE Email='" + email+ "' or Username='"+username+"'";
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor verify = db.rawQuery(sql,null);
        boolean check = false;

        if(verify.moveToFirst()) { check = true; }

        else { check = false; }
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

    public boolean DeleteModule(int num)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String numCon = String.valueOf(num);
        db.delete(Modules.MODULE_TABLE_NAME,  Modules.MODULE_COLUMN_NUMBER + " = ? ",new String[]{numCon});
        return  true;
    }

    public Cursor trackUpdate()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql="SELECT * FROM " +User_Track.USER_TRACK_TABLE_NAME;
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }
    public Cursor trackModuleRes()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql="SELECT * FROM " +Module_Results.MODULE_RESULT_TABLE_NAME;
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }
    public Cursor trackModule()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql="SELECT * FROM " +Modules.MODULE_TABLE_NAME;
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }
    public Cursor getModuleNum()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT "+Modules.MODULE_COLUMN_NUMBER+ " FROM "+ Modules.MODULE_TABLE_NAME;
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }
    public Cursor getFilesInfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+ AudioAndImages.AudandImg_TABLE_NAME;// + " LIMIT 1" ;
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }

    public Cursor getAudioFile()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT "+AudioAndImages.AudandImg_ID+" FROM "+AudioAndImages.AudandImg_TABLE_NAME;
        Cursor cursor = db.rawQuery(sql,null);
        return  cursor;
    }


    //Created a new cursor method for summary report
    public Cursor getUserListInfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT "+User.COLUMN_FULL_NAME +","+User.COLUMN_USERNAME +","+User.COLUMN_EMAIL+","
                +User.COLUMN_CREATED+ " FROM " +User.USER_TABLE_NAME;

        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    public Cursor getImageSession(int module, int lesson)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql="SELECT * FROM "+ AudioAndImages.AudandImg_TABLE_NAME
                    + " WHERE "+ AudioAndImages.AudandImg_MODULE+ " = "+module
                    +" AND "+ AudioAndImages.AudandImg_LESSON_COLUMN+" = "+lesson;
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_TABLE);
        db.execSQL(Modules.CREATE_MODULE_TABLE);
        db.execSQL(Module_Results.CREATE_MODULE_RESULT);
        db.execSQL(User_Track.CREATE_USER_TRACK_TABLE);
        db.execSQL(AudioAndImages.CREATE_AudandImg_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}

