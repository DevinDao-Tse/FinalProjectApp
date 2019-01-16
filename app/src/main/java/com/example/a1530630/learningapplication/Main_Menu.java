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

import com.example.a1530630.learningapplication.Database.SQLiteManage;

import org.w3c.dom.Text;

public class Main_Menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public DrawerLayout dl;
    public ActionBarDrawerToggle t;
    TextView dev,dev2,dev1_1;
    TextView sha;

    SQLiteManage db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);
        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        db = new SQLiteManage(this);

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
        dev2 = findViewById(R.id.textView3);
        dev1_1 = findViewById(R.id.textView4);


        dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(getApplicationContext(), Session.class);
               // i.putExtra("Audio", dev.getContentDescription().toString());
               // startActivity(i);
                LessonBox();
            }
        });

        dev2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Session.class);
                i.putExtra("Audio", dev2.getContentDescription().toString());
                startActivity(i);
            }
        });

        dev1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Session.class);
                i.putExtra("Audio", dev1_1.getContentDescription().toString());
                startActivity(i);
            }
        });
    }
    public void LessonBox()
    {
        final Dialog BOX = new Dialog(this);
        BOX.requestWindowFeature(Window.FEATURE_NO_TITLE);
        BOX.setContentView(R.layout.module_lessons);
        BOX.show();

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
                i = new Intent(this, Profile.class);
                startActivity(i);
                return true;
            }
            case R.id.nav_settings:
            {

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
