package com.example.a1530630.learningapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.User;

import java.util.ArrayList;

public class SummaryReport extends AppCompatActivity
{
    SQLiteManage db;
    private TableLayout tableLayout;
    ImageButton backMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_report);

        db = new SQLiteManage(this);

        backMenu = (ImageButton) findViewById(R.id.backBtn);

        tableLayout = (TableLayout)findViewById(R.id.listData);

        Cursor cursor = db.getUserListInfo();

        //Fills in the table with rows and columns
        if(cursor.moveToFirst())
        {

             do
            {
                View tableRow = LayoutInflater.from(this).inflate(R.layout.activity_summary_adapter,null, false);
                TextView name = tableRow.findViewById(R.id.FullName);
                TextView userTitle = tableRow.findViewById(R.id.userNam13);
                TextView emailTitle = tableRow.findViewById(R.id.emailAddress);
                TextView dateTitle = tableRow.findViewById(R.id.dateText);

                name.setText(cursor.getString(1));
                userTitle.setText(cursor.getString(2));
                emailTitle.setText(cursor.getString(3));
                dateTitle.setText(cursor.getString(4));
                tableLayout.addView(tableRow);
            }
            while(cursor.moveToNext());


        }
    }

    private void backToMainMenu()
    {
        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SummaryReport.this, Main_Menu.class);
                startActivity(i);
            }
        });
    }


}
