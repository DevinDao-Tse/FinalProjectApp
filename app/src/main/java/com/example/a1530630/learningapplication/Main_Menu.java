package com.example.a1530630.learningapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.AudioAndImages;
import com.example.a1530630.learningapplication.Models.Module_Results;
import com.example.a1530630.learningapplication.Models.Modules;
import com.example.a1530630.learningapplication.Models.User_Track;
import com.example.a1530630.learningapplication.Models.User;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class Main_Menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public DrawerLayout dl;
    public ActionBarDrawerToggle t;
    TextView less,mod;
    String moduleHolder;
    Intent idk;
    SharedPreferences pref;
    public Dialog BOX;
    public Button show,show2,show3,show4;
    public LinearLayout lay;
    SQLiteManage db;
    ImageView play,picture;
    String path2;
    NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);
        getSupportActionBar().hide();

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        db = new SQLiteManage(this);

        //new user will go straight to tutorial
        pref = this.getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);
        if(pref.getBoolean("New User",false) == true)
        {
            Intent i = new Intent(getApplicationContext(),Tutorial.class);
            startActivity(i);
        }

        lay = findViewById(R.id.Modules);

        show = (Button)findViewById(R.id.Showbtn);
        show2 = (Button)findViewById(R.id.Showbtn2);
        show3 = (Button)findViewById(R.id.showbtn3);
        show4 = (Button)findViewById(R.id.showbtn4);

       // play = (ImageView)findViewById(R.id.PlayButton2);
        String path = getCacheDir().getAbsolutePath();

        ViewAll();
        ViewAll2();
        ViewAll3();
        ViewAll4();
        readFromDB();

//        //play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { playMp3(); }
//        });

        idk = new Intent(getApplicationContext(), Session2.class);
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

        nv = findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(this);

        
    }

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private void playMp3() {
        try {
            byte[] mp3SoundByteArray = new byte[35000];
            Cursor cursor = db.getFilesInfo();
            if(cursor.moveToFirst()) { mp3SoundByteArray = cursor.getBlob(1);}
            File dir = getFilesDir();String path = getFilesDir().getAbsolutePath();
            File tempMp3 = File.createTempFile("testing", ".mp3");path2 = tempMp3.getAbsolutePath();
            FileOutputStream fos = new FileOutputStream(tempMp3);fos.write(mp3SoundByteArray);fos.flush();fos.close();

        } catch (IOException ex) { String s = ex.toString();ex.printStackTrace(); }

    }

    @Override
    public void onBackPressed(){ //super.onBackPressed(); //comment out if you want back button to do something
    }

    public void readFromDB()
    {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Cursor cursor = db.ReadModule();
        if(cursor.moveToFirst())
        {
            do
            {
                int txt = cursor.getInt(cursor.getColumnIndex(Modules.MODULE_COLUMN_NUMBER));

                TextView textView = new TextView(this);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                textView.setLayoutParams(lparams);
                textView.setText("Module "+ txt+" ");
                textView.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite));
                String con = String.valueOf(txt);
                textView.setContentDescription(con);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) { showLessons(view); }
                });
                lay.addView(textView);

            }while(cursor.moveToNext());
        }
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

    public void noLessons(View v)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(Main_Menu.this).create();
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

    //selecting lessons and passing parameters in intent
    public void goingtoLesson(View v)
    {
        less = (TextView)v;
        less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int useID = pref.getInt("UserID",0);
                int num = Integer.parseInt(moduleHolder);

                Cursor cursor = db.getImageSession(Integer.parseInt(moduleHolder),
                        getLesson(less.getContentDescription().toString()));
                if(cursor.moveToFirst())
                {
                    if(db.setModule(num,useID) && db.setTrack(num, useID))
                    {
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
                        idk.putExtra("Audio", "0");
                        idk.putExtra("Module",moduleHolder);
                        idk.putExtra("Lesson",less.getContentDescription().toString());
                        startActivity(idk);
                    }
                }
                else { noLessons(view); }
            }
        });
    }
    private int getLesson(String lesson)
    {
        int les=0;
        if(lesson.equals("Lesson1")){ les =1;}
        else if (lesson.equals("Lesson2")){ les =2;}
        else if (lesson.equals("Lesson3")){ les =3;}
        else if (lesson.equals("Lesson4")){ les =4;}
        else if (lesson.equals("Lesson5")){ les =5;}
        return les;
    }

    public void ViewAll()
    {
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = db.trackUpdate();
                if(cursor.getCount() == 0) { return; }
                StringBuffer stringBuffer = new StringBuffer();
                while(cursor.moveToNext())
                {
                    stringBuffer.append("UserID "+ cursor.getInt(0) + "\n");
                    stringBuffer.append("ModuleID "+ cursor.getInt(1)+ "\n");
                    stringBuffer.append("Results "+ cursor.getInt(2)+ "%\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(Main_Menu.this);
                builder.setCancelable(true);
                builder.setMessage(stringBuffer.toString());
                builder.show();
            }
        });
    }

    public void ViewAll2()
    {
        show2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = db.trackModuleRes();
                if(cursor.getCount() == 0) { return; }
                StringBuffer stringBuffer = new StringBuffer();
                while(cursor.moveToNext())
                {
                    stringBuffer.append("UserID "+ cursor.getInt(2)+ "\n");
                    stringBuffer.append("ModuleID "+ cursor.getInt(1)+ "\n");
                    stringBuffer.append("ModuleResID "+ cursor.getInt(0) + "\n");
                    stringBuffer.append("Lesson1 "+ cursor.getInt(3)+ "%\n");
                    stringBuffer.append("Lesson2 "+ cursor.getInt(4)+ "%\n");
                    stringBuffer.append("Lesson3 "+ cursor.getInt(5)+ "%\n");
                    stringBuffer.append("Lesson4 "+ cursor.getInt(6)+ "%\n");
                    stringBuffer.append("Lesson5 "+ cursor.getInt(7)+ "%\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(Main_Menu.this);
                builder.setCancelable(true);
                builder.setMessage(stringBuffer.toString());
                builder.show();
            }
        });
    }

    public void ViewAll3()
    {
        show3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = db.trackModule();
                if(cursor.getCount() == 0) { return; }
                StringBuffer stringBuffer = new StringBuffer();
                while(cursor.moveToNext())
                {
                    stringBuffer.append("ModuleID "+ cursor.getInt(0) + "\n");
                    stringBuffer.append("ModuleNum "+ cursor.getInt(1)+ "\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(Main_Menu.this);
                builder.setCancelable(true);
                builder.setMessage(stringBuffer.toString());
                builder.show();
            }
        });
    }

    public void ViewAll4()
    {
        show4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = db.getFilesInfo();
                if(cursor.getCount() == 0) { return; }
                StringBuffer stringBuffer = new StringBuffer();
                while(cursor.moveToNext())
                {
                    // byte[] audValue = cursor.getBlob(1);
                    int mod = cursor.getInt(1);
                    int les = cursor.getInt(2);
                    byte[] imgValue = cursor.getBlob(4);

                    stringBuffer.append("File ID: "+ cursor.getInt(0) + "\n");
                    stringBuffer.append("Module: "+ String.valueOf(mod)+" \n");
                    stringBuffer.append("Lesson: "+ String.valueOf(les)+" \n");
                    // stringBuffer.append("Audio: "+audValue.length+ " || "+audValue.toString()+ "\n");
                    stringBuffer.append("Image: "+ imgValue.length+ " || "+imgValue.toString()+" \n\n");

                }
                AlertDialog.Builder builder = new AlertDialog.Builder(Main_Menu.this);
                builder.setCancelable(true);
                builder.setMessage(stringBuffer.toString());
                builder.show();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) { if(t.onOptionsItemSelected(item)) return true;return super.onOptionsItemSelected(item); }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        Intent i;

        //Checking to see if username has admin or not
        SharedPreferences preferences = getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);
        String adminUser = preferences.getString("Username",null);

        if(adminUser.contains("admin"))
        {
            switch (menuItem.getItemId())
            {

                case R.id.nav_menu: {
                    i = new Intent(this, Main_Menu.class);
                    startActivity(i);
                    return true;
                }
                case R.id.nav_profile: {
                    i = new Intent(this, Profile.class);
                    startActivity(i);
                    return true;
                }

                case R.id.nav_summary: {
                    i = new Intent(this, SummaryReport.class);
                    startActivity(i);
                    return true;
                }

                case R.id.nav_exit: {
                    SharedPreferences settings = getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.clear();
                    editor.commit();
                    i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                    return true;
                }
                case R.id.nav_add: {
                    i = new Intent(getApplicationContext(), Store.class);
                    startActivity(i);
                    return true;
                }
                case R.id.nav_tutorial: {
                    i = new Intent(getApplicationContext(), Tutorial.class);
                    startActivity(i);
                    return true;
                }

            }
        }
        else
        {
            switch (menuItem.getItemId())
            {

                case R.id.nav_menu: {
                    i = new Intent(this, Main_Menu.class);
                    startActivity(i);
                    return true;
                }
                case R.id.nav_profile: {
                    i = new Intent(this, Profile.class);
                    startActivity(i);
                    return true;
                }


                case R.id.nav_exit: {
                    SharedPreferences settings = getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.clear();
                    editor.commit();
                    i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                    return true;
                }

                case R.id.nav_tutorial: {
                    i = new Intent(getApplicationContext(), Tutorial.class);
                    startActivity(i);
                    return true;
                }


            }
        }

            return true;
        }

}
