package com.example.a1530630.learningapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.User;

import java.util.ArrayList;

public class SummaryReport extends AppCompatActivity
{
    SQLiteManage db;
    private ListView listView;
    Button backMenu;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_report);
        db = new SQLiteManage(this);


        listView = (ListView) findViewById(R.id.SummaryList);

        ArrayList <User> summaryArray = new ArrayList<>();

        Cursor cursor = db.getUserListInfo();

        //Fills in the list
        if(cursor.moveToFirst())
        {
            do
            {
                User users = new User();
                users.setFullName(cursor.getString(cursor.getColumnIndex(users.COLUMN_FULL_NAME)));
                users.setUsername(cursor.getString(cursor.getColumnIndex(users.COLUMN_USERNAME)));
                users.setEmail(cursor.getString(cursor.getColumnIndex(users.COLUMN_EMAIL)));
                users.setCreated(cursor.getString(cursor.getColumnIndex(users.COLUMN_CREATED)));

                summaryArray.add(users);

            }
            while(cursor.moveToNext());
        }

        //Sets the adapter
        SummaryAdapter summaryAdapter = new SummaryAdapter(this, R.layout.activity_summary_adapter,summaryArray);
        listView.setAdapter(summaryAdapter);

    }


    public void backToMainMenu(View view)
    {
        Intent i = new Intent(SummaryReport.this, Main_Menu.class);
        startActivity(i);

    }


}
