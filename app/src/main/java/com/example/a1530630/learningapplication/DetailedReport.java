package com.example.a1530630.learningapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.Module_Results;
import com.example.a1530630.learningapplication.Models.User;

import java.util.ArrayList;
import java.util.List;

public class DetailedReport extends AppCompatActivity
{
    SQLiteManage db;
    private ListView listView;
    Button backMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_report);

        listView = (ListView) findViewById(R.id.DetailedView);

        //User
        ArrayList<User> detailedUsers = new ArrayList<>();

        //Might have to create another model for User/Module_Results0*

        //Modules_Results
        ArrayList<Module_Results> detailedModules = new ArrayList<>();

        Cursor cursor = db.getUserModuleProcess();

        if(cursor.moveToFirst())
        {
            do
                {
                    User users = new User();
                    users.setUserID(cursor.getInt(cursor.getColumnIndex(users.COLUMN_ID)));
                    users.setPassword(cursor.getString(cursor.getColumnIndex(users.COLUMN_PASSWORD)));
                    users.setUsername(cursor.getString(cursor.getColumnIndex(users.COLUMN_USERNAME)));
                    users.setFullName(cursor.getString(cursor.getColumnIndex(users.COLUMN_FULL_NAME)));
                    users.setEmail(cursor.getString(cursor.getColumnIndex(users.COLUMN_EMAIL)));
                    users.setCreated(cursor.getString(cursor.getColumnIndex(users.COLUMN_CREATED)));

                    detailedUsers.add(users);

                    Module_Results results = new Module_Results();
                    results.setModuleID(cursor.getInt(cursor.getColumnIndex(results.MODULE_RESULT_COLUMN_MODULE_ID)));
                    results.setLesOne(cursor.getInt(cursor.getColumnIndex(results.MODULE_RESULT_COLUMN_LESSON_ONE)));
                    results.setLesTwo(cursor.getInt(cursor.getColumnIndex(results.MODULE_RESULT_COLUMN_LESSON_TWO)));
                    results.setLesThree(cursor.getInt(cursor.getColumnIndex(results.MODULE_RESULT_COLUMN_LESSON_THREE)));
                    results.setLesFour(cursor.getInt(cursor.getColumnIndex(results.MODULE_RESULT_COLUMN_LESSON_FOUR)));
                    results.setLesFive(cursor.getInt(cursor.getColumnIndex(results.MODULE_RESULT_COLUMN_LESSON_FIVE)));

                    detailedModules.add(results);


                }
                while(cursor.moveToNext());


        }




    }





}
