package com.example.a1530630.learningapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.Module_Results;
import com.example.a1530630.learningapplication.Models.User_Track;

public class Main_Menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public DrawerLayout dl;
    public ActionBarDrawerToggle t;
    TextView w1;
    TextView less,mod;
    String moduleHolder;
    Intent idk;
    SharedPreferences pref;
    public Dialog BOX;
    public Button test, show,show2;
    public EditText testnum;
    public int num=0;
    public LinearLayout lay;
    public int count=3;
    public TextView textView;
    public boolean textadded;

    long ModResID= 0;
    SQLiteManage db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);
        getSupportActionBar().hide();

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        db = new SQLiteManage(this);

        pref = this.getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);
        test = (Button)findViewById(R.id.Testbtn);

        lay = findViewById(R.id.Modules);

        show = (Button)findViewById(R.id.Showbtn);
        show2 = (Button)findViewById(R.id.Showbtn2);

        ViewAll();
        ViewAll2();


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
        idk = new Intent(getApplicationContext(), Session2.class);
    }


    public void addnewBox(View v)
    {
            count++;
            lay.addView(createNewTextView(count));
    }

    private View.OnClickListener onClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                count++;
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textView = new TextView(getApplicationContext());
                textView.setLayoutParams(lparams);
                textView.setText("Module " + count+" ");
                String con = String.valueOf(count);
                textView.setContentDescription(con);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showLessons(view);
                    }
                });
                lay.addView(textView);
                // lay.addView(createNewTextView(count));
            }
        };
    }

    @Override
    public void onBackPressed(){
        //super.onBackPressed(); //comment out if you want back button to do something
    }

    private TextView createNewTextView(int text) {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        textView.setText("Module " + text+" ");
        String con = String.valueOf(text);
        textView.setContentDescription(con);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLessons(view);
            }
        });
        textadded =true;
        return textView;
    }

    //setting imageview/textview to onclick method
    //show box for lessons
    public void showLessons(View v)
    {
        BOX = new Dialog(Main_Menu.this);
        BOX.requestWindowFeature(Window.FEATURE_NO_TITLE);
        BOX.setContentView(R.layout.module_lessons);
        mod =(TextView)v;
        moduleHolder = mod.getContentDescription().toString();
        BOX.show();
    }

    //selecting lessons and passing parameters in intent
    public void goingtoLesson(View v)
    {
        less = (TextView)v;
        less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int useID = pref.getInt("UserID",0);
                int num = Integer.parseInt(moduleHolder);
                if(db.setModule(num,useID) && db.setTrack(num, useID))
                {
                   //Cursor cursor = db.getModuleResID(useID);
                    /*if(cursor.moveToFirst())
                    {
                        ModResID = cursor.getLong(cursor.getColumnIndex(Module_Results.MODULE_RESULT_COLUMN_MODULE_RES_ID));
                    }*/
                    //idk.putExtra("ModResID", ModResID);
                    idk.putExtra("Audio", "0");
                    idk.putExtra("Module",moduleHolder);
                    idk.putExtra("Lesson",less.getContentDescription().toString());
                    startActivity(idk);
                }
                else
                {
                    Module_Results res = new Module_Results(useID);
                    User_Track track = new User_Track(useID);
                    db.createResult(res,num);
                    db.createTrack(track,num);
                    //idk.putExtra("ModResID", ModResID);
                    idk.putExtra("Audio", "0");
                    idk.putExtra("Module",moduleHolder);
                    idk.putExtra("Lesson",less.getContentDescription().toString());
                    startActivity(idk);

                }

            }
        });
    }
    public void ViewAll()
    {
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = db.trackUpdate();
                if(cursor.getCount() == 0)
                {
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while(cursor.moveToNext())
                {
                    stringBuffer.append("UserID "+ cursor.getInt(0) + "\n");
                    stringBuffer.append("ModuleID "+ cursor.getInt(1)+ "\n");
                    stringBuffer.append("Results "+ cursor.getInt(2)+ "%\n\n");
                }
                showMsg(stringBuffer.toString());
            }
        });
    }

    public void showMsg(String msg)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(msg);
        builder.show();
    }
    public void ViewAll2()
    {
        show2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = db.trackModuleRes();
                if(cursor.getCount() == 0)
                {
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while(cursor.moveToNext())
                {
                    stringBuffer.append("ModuleResID "+ cursor.getInt(0) + "\n");
                    stringBuffer.append("ModuleID "+ cursor.getInt(1)+ "\n");
                    stringBuffer.append("UserID "+ cursor.getInt(2)+ "\n");
                    stringBuffer.append("Lesson1 "+ cursor.getInt(3)+ "%\n");
                    stringBuffer.append("Lesson2 "+ cursor.getInt(4)+ "%\n\n");
                }
                showMsg2(stringBuffer.toString());
            }
        });
    }

    public void showMsg2(String msg)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(msg);
        builder.show();
    }

/////////////////////////DO NOT ERASE PLZ///////////////////////////////////////
    //Lessons dialog box when clicking the module
    public void LessonBox(final String mod,View v) { BOX = new Dialog(Main_Menu.this);BOX.requestWindowFeature(Window.FEATURE_NO_TITLE);BOX.setContentView(R.layout.module_lessons);BOX.show(); }
    //selecting word for audio
    public void WordBox(final String le) { final Dialog WORD = new Dialog(Main_Menu.this);WORD.requestWindowFeature(Window.FEATURE_NO_TITLE);WORD.setContentView(R.layout.lessons_words);w1 = (TextView) WORD.findViewById(R.id.Word1);w1.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) { Intent i = new Intent(getApplicationContext(), Session.class);i.putExtra("Audio", w1.getContentDescription().toString());i.putExtra("Module",moduleHolder);i.putExtra("Lesson",le);startActivity(i); }});WORD.show(); }
//////////////////////////DO NOT ERASE PLEASE////////////////////////////////////

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
            case R.id.nav_add:
               i = new Intent(getApplicationContext(),Store.class);
               startActivity(i);
               return true;
        }

        return true;
    }
}
