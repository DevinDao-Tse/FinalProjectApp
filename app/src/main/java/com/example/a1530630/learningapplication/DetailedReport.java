package com.example.a1530630.learningapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.UserModuleProcess;


import java.util.ArrayList;


public class DetailedReport extends AppCompatActivity {
    SQLiteManage db;
    private ListView listview;
    Button backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_report);
        db = new SQLiteManage(this);

        listview = (ListView) findViewById(R.id.DetailedView);

        ArrayList<UserModuleProcess> userModuleProcessArrayList = new ArrayList<>();

        Cursor umCursor = db.getUserModuleProcess();

        if (umCursor.moveToFirst()) {
            do {
                UserModuleProcess umProcess = new UserModuleProcess();
                umProcess.setUserID(umCursor.getInt(umCursor.getColumnIndex(umProcess.UM_COLUMN_USER_ID)));
                umProcess.setUserN(umCursor.getString(umCursor.getColumnIndex(umProcess.UM_COLUMN_USERNAME)));
                umProcess.setPassW(umCursor.getString(umCursor.getColumnIndex(umProcess.UM_COLUMN_PASSWORD)));
                umProcess.setFullN(umCursor.getString(umCursor.getColumnIndex(umProcess.UM_COLUMN_FULL_NAME)));
                umProcess.setEmailAddress(umCursor.getString(umCursor.getColumnIndex(umProcess.UM_COLUMN_EMAIL)));
                umProcess.setDateCompleted(umCursor.getString(umCursor.getColumnIndex(umProcess.UM_COLUMN_CREATED)));
                umProcess.setModuleID(umCursor.getInt(umCursor.getColumnIndex(umProcess.UM_COLUMN_MODULE_ID)));
                umProcess.setLessonOne(umCursor.getInt(umCursor.getColumnIndex(umProcess.UM_COLUMN_LESSON_ONE)));
                umProcess.setLessonTwo(umCursor.getInt(umCursor.getColumnIndex(umProcess.UM_COLUMN_LESSON_TWO)));
                umProcess.setLessonThree(umCursor.getInt(umCursor.getColumnIndex(umProcess.UM_COLUMN_LESSON_THREE)));
                umProcess.setLessonFour(umCursor.getInt(umCursor.getColumnIndex(umProcess.UM_COLUMN_LESSON_FOUR)));
                umProcess.setLessonFive(umCursor.getInt(umCursor.getColumnIndex(umProcess.UM_COLUMN_LESSON_FIVE)));

                userModuleProcessArrayList.add(umProcess);

            }
            while (umCursor.moveToNext());
        }

        //Sets the adapter
        DetailedAdapter detailedAdapter = new DetailedAdapter(this, R.layout.activity_detailed_adapter, userModuleProcessArrayList);
        listview.setAdapter(detailedAdapter);

    }


    public void backToMainMenu(View view) {
        Intent i = new Intent(DetailedReport.this, Main_Menu.class);
        startActivity(i);

    }
}