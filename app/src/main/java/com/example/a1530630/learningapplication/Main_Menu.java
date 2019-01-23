package com.example.a1530630.learningapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.Module_Results;
import com.example.a1530630.learningapplication.Models.Modules;

import org.w3c.dom.Text;

public class Main_Menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public DrawerLayout dl;
    public ActionBarDrawerToggle t;
    TextView dev;
    TextView w1,w2,w3,w4,w5;
    TextView l1,l2,l3,l4,l5;
    String moduleHolder,lessonHolder;
    SharedPreferences pref;

    SQLiteManage db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);
        getSupportActionBar().hide();
        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        db = new SQLiteManage(this);

        pref = this.getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);

        t = new ActionBarDrawerToggle(this, dl,R.string.nav_open, R.string.nav_close);
        dl.addDrawerListener(t);
        t.syncState(); //getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dl.addDrawerListener(new DrawerLayout.DrawerListener()
        {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) { }
            @Override
            public void onDrawerOpened(@NonNull View view)
            {
                SharedPreferences settings = getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);
                String userName = settings.getString("Username",null);
                TextView user = findViewById(R.id.nav_header_textView);
                user.setText(userName);
            }
            @Override
            public void onDrawerClosed(@NonNull View view) {}
            @Override
            public void onDrawerStateChanged(int i) {}
        });

        NavigationView nv = findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(this);

        dev = findViewById(R.id.textView1);

        dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moduleHolder = dev.getContentDescription().toString();
                LessonBox(moduleHolder);
            }
        });

    }

    //Lessons dialog box when clicking the module
    public void LessonBox(final String mod)
    {
        final Dialog BOX = new Dialog(Main_Menu.this);
        BOX.requestWindowFeature(Window.FEATURE_NO_TITLE);
        BOX.setContentView(R.layout.module_lessons);

        //lessons items
        l1 = (TextView) BOX.findViewById(R.id.Lesson1);
        l2 = (TextView) BOX.findViewById(R.id.Lesson2);
        l3 = (TextView) BOX.findViewById(R.id.Lesson3);

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int useID = pref.getInt("UserID",0);
                int num = Integer.parseInt(moduleHolder);
                if(db.setModule(num,useID))
                {
                    Intent i = new Intent(getApplicationContext(), Session2.class);
                    i.putExtra("Audio", "0");
                    i.putExtra("Module",moduleHolder);
                    i.putExtra("Lesson",l1.getContentDescription().toString());
                    startActivity(i);
                }
                else
                    {

                        Module_Results res = new Module_Results(useID);
                        db.createResult(res,num);
                        Intent i = new Intent(getApplicationContext(), Session2.class);
                        i.putExtra("Audio", "0");
                        i.putExtra("Module",moduleHolder);
                        i.putExtra("Lesson",l1.getContentDescription().toString());
                        startActivity(i);
                    }

                //lessonHolder = l1.getContentDescription().toString();
               //WordBox(lessonHolder); //passing the selected lesson

            }
        });


        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int useID = pref.getInt("UserID",0);
                int num = Integer.parseInt(moduleHolder);
                if(db.setModule(num,useID ))
                {
                    Intent i = new Intent(getApplicationContext(), Session2.class);
                    i.putExtra("Audio", "0");
                    i.putExtra("Module",moduleHolder);
                    i.putExtra("Lesson",l2.getContentDescription().toString());
                    startActivity(i);
                }
                else
                {
                    Module_Results res = new Module_Results(useID);
                    db.createResult(res,num);
                    Intent i = new Intent(getApplicationContext(), Session2.class);
                    i.putExtra("Audio", "0");
                    i.putExtra("Module",moduleHolder);
                    i.putExtra("Lesson",l2.getContentDescription().toString());
                    startActivity(i);
                }
            }
        });

        BOX.show();
    }

    //selecting word for audio
    public void WordBox(final String le)
    {
        final Dialog WORD = new Dialog(Main_Menu.this);
        WORD.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WORD.setContentView(R.layout.lessons_words);

        w1 = (TextView) WORD.findViewById(R.id.Word1);
        w1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Session.class);
                i.putExtra("Audio", w1.getContentDescription().toString());
                i.putExtra("Module",moduleHolder);
                i.putExtra("Lesson",le);
                startActivity(i);
            }
        });

        w2 = (TextView) WORD.findViewById(R.id.Word2);
        w2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Session.class);
                i.putExtra("Audio", w2.getContentDescription().toString());
                i.putExtra("Module",moduleHolder);
                i.putExtra("Lesson",le);
                startActivity(i);
            }
        });

        w3 = (TextView) WORD.findViewById(R.id.Word1);
        w3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Session.class);
                i.putExtra("Audio", w3.getContentDescription().toString());
                i.putExtra("Module",moduleHolder);
                i.putExtra("Lesson",le);
                startActivity(i);
            }
        });

        w4 = (TextView) WORD.findViewById(R.id.Word1);
        w4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Session.class);
                i.putExtra("Audio", w4.getContentDescription().toString());
                i.putExtra("Module",moduleHolder);
                i.putExtra("Lesson",le);
                startActivity(i);
            }
        });

        w5 = (TextView) WORD.findViewById(R.id.Word1);
        w5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Session.class);
                i.putExtra("Audio", w5.getContentDescription().toString());
                i.putExtra("Module",moduleHolder);
                i.putExtra("Lesson",le);
                startActivity(i);
            }
        });

        WORD.show();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if(t.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent i;
        switch (menuItem.getItemId())
        {
            case R.id.nav_menu:
            {
                i = new Intent(this,Main_Menu.class);
                startActivity(i);
                return true;
            }
            case R.id.nav_profile:
            {
                return true;
            }
            case R.id.nav_settings:
            {
                i = new Intent(this, User_setting.class);
                startActivity(i);
                return true;

            }
            case R.id.nav_exit:
            {
                SharedPreferences settings = getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.commit();
                i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
                return true;
            }
        }

        return true;
    }
}
