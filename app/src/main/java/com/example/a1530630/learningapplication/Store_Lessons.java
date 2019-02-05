package com.example.a1530630.learningapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.Module_Results;
import com.example.a1530630.learningapplication.Models.Modules;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Store_Lessons extends AppCompatActivity {

    ImageButton homebtn;
    SQLiteManage db;
    Button edit1,edit2,edit3,edit4,edit5;
    TextView view;
    Intent i;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store__lessons);
        db = new SQLiteManage(this);
        homebtn = (ImageButton)findViewById(R.id.HomeButton);

        edit1 = (Button)findViewById(R.id.EditBtn1);
        edit2 = (Button)findViewById(R.id.EditBtn2);
        edit3 = (Button)findViewById(R.id.EditBtn3);
        edit4 = (Button)findViewById(R.id.EditBtn4);
        edit5 = (Button)findViewById(R.id.EditBtn5);

        Intent before = getIntent();
        num = getIntent().getIntExtra("Module",0);
        i = new Intent(this, Editing.class);
        view = (TextView)findViewById(R.id.textView2);
        view.setText(String.valueOf(num));
        homeButton();

        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("Module",num);
                i.putExtra("Lesson", edit1.getContentDescription().toString());
                startActivity(i);
            }
        });

        edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("Module",num);
                i.putExtra("Lesson", edit2.getContentDescription().toString());
                startActivity(i);
            }
        });

        edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("Module",num);
                i.putExtra("Lesson", edit3.getContentDescription().toString());
                startActivity(i);
            }
        });

        edit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("Module",num);
                i.putExtra("Lesson", edit4.getContentDescription().toString());
                startActivity(i);
            }
        });

        edit5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("Module",num);
                i.putExtra("Lesson", edit5.getContentDescription().toString());
                startActivity(i);
            }
        });

    }
    private void homeButton(){homebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Store_Lessons.this,Store.class);
            startActivity(i);
        }
    });}
}
