package com.example.a1530630.learningapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
    Button edit1,edit2,edit3,edit4,edit5,buts;
    Button view1,view2,view3,view4,view5,edits;
    TextView view;
    Intent i,e;
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

        view1 = (Button)findViewById(R.id.viewBtn1);
        view2 = (Button)findViewById(R.id.viewBtn2);
        view3 = (Button)findViewById(R.id.viewBtn3);
        view4 = (Button)findViewById(R.id.viewBtn4);
        view5 = (Button)findViewById(R.id.viewBtn5);

        Intent before = getIntent();
        num = getIntent().getIntExtra("Module",0);
        i = new Intent(this, Editing.class);
        e = new Intent(this,View_Lessons.class);

        view = (TextView)findViewById(R.id.textView2);
        view.setText(String.valueOf(num));
        homeButton();

        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //i.putExtra("Module",num);i.putExtra("Lesson", edit1.getContentDescription().toString());startActivity(i);
                LessonEdit(view);
            }
        });

        edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { LessonEdit(view); }});

        edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { LessonEdit(view); }});

        edit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { LessonEdit(view); }});

        edit5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { LessonEdit(view); }});

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { SeeLesson(view,Integer.parseInt(view1.getContentDescription().toString())); }});

        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { SeeLesson(view,Integer.parseInt(view2.getContentDescription().toString())); }});

        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { SeeLesson(view,Integer.parseInt(view3.getContentDescription().toString())); }});

        view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { SeeLesson(view,Integer.parseInt(view4.getContentDescription().toString())); }});

        view5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { SeeLesson(view,Integer.parseInt(view5.getContentDescription().toString())); }});

    }
    private void homeButton(){homebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Store_Lessons.this,Store.class);
            startActivity(i);
        }
    });}

    private void LessonEdit(View v)
    {
        buts = (Button)v;
        buts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("Module",num);
                i.putExtra("Lesson", Integer.parseInt(buts.getContentDescription().toString()));
                startActivity(i);
            }
        });
    }

    private void SeeLesson(View v, final int lesson)
    {
        edits = (Button)v;
        edits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor = db.getImgsInfo(num,lesson);
                if(cursor.moveToFirst())
                {
                    e.putExtra("Module",num);
                    e.putExtra("Lesson", Integer.parseInt(edits.getContentDescription().toString()));
                    startActivity(e);
                }
                else
                    {
                       noLessons(view);
                    }
            }
        });
    }
    @Override
    public void onBackPressed() { }
    
    public void noLessons(View v)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(Store_Lessons.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("No Images set");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}