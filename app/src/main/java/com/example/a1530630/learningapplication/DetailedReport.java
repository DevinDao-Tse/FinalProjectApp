package com.example.a1530630.learningapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.a1530630.learningapplication.Database.SQLiteManage;



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
        db = new SQLiteManage(this);

        listView = (ListView) findViewById(R.id.DetailedView);

        Cursor detailList = db.getUserModuleProcess();

        DetailedAdapter detailedAdapter = new DetailedAdapter(this,detailList);

        listView.setAdapter(detailedAdapter);

        //detailedAdapter.changeCursor(detailList);

        }


    }




