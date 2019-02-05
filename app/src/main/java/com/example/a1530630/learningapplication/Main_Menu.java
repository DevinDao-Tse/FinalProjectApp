package com.example.a1530630.learningapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    TextView w1,aud;
    TextView less,mod;
    String moduleHolder;
    Intent idk;
    SharedPreferences pref;
    public Dialog BOX;
    public Button show,show2,show3,show4,play2;
    public LinearLayout lay;
    SQLiteManage db;
    ImageView play,picture;
    String path2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);
        getSupportActionBar().hide();

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        db = new SQLiteManage(this);

        pref = this.getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);

        lay = findViewById(R.id.Modules);

        show = (Button)findViewById(R.id.Showbtn);
        show2 = (Button)findViewById(R.id.Showbtn2);
        show3 = (Button)findViewById(R.id.showbtn3);
        show4 = (Button)findViewById(R.id.showbtn4);
        play2 = (Button)findViewById(R.id.button2);

        play = (ImageView)findViewById(R.id.PlayButton2);
        picture = (ImageView) findViewById(R.id.imageView);
        aud = (TextView) findViewById(R.id.textView);
        String path = getCacheDir().getAbsolutePath();
        aud.setText(path);

        ViewAll();
        ViewAll2();
        ViewAll3();
        ViewAll4();
        readFromDB();



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { playMp3(); }
        });

        play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer.setDataSource("/data/data/com.example.a1530630.learningapplication/cache/testing273537506.mp3");
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

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

        NavigationView nv = findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(this);
    }

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private void playMp3() {
        try {
            byte[] mp3SoundByteArray = new byte[35000];
            byte[] img = null;

            Cursor cursor = db.getFilesInfo();
            if(cursor.moveToFirst()) { mp3SoundByteArray = cursor.getBlob(1); img = cursor.getBlob(2);}
          //  Bitmap bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
           // picture.setImageBitmap(bitmap);

            File dir = getFilesDir();
            String path = getFilesDir().getAbsolutePath();
            File tempMp3 = File.createTempFile("testing", ".mp3");
            path2 = tempMp3.getAbsolutePath();

            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(mp3SoundByteArray);
            fos.flush();
            fos.close();


        } catch (IOException ex) { String s = ex.toString();ex.printStackTrace(); }

    }
    @Override
    protected void onStop()
    {
        super.onStop();
        mediaPlayer.release();
        mediaPlayer = null;

    }

    @Override
    public void onBackPressed(){
        //super.onBackPressed(); //comment out if you want back button to do something
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
                textView.setLayoutParams(lparams);
                textView.setText("Module "+ txt+" ");
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
        });
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
                    stringBuffer.append("Lesson2 "+ cursor.getInt(4)+ "%\n\n");
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
                    byte[] audValue = cursor.getBlob(1);
                    byte[] imgValue = cursor.getBlob(2);

                    stringBuffer.append("File ID: "+ cursor.getInt(0) + "\n");
                    stringBuffer.append("Audio: "+audValue.length+ " || "+audValue.toString()+ "\n");
                  //  stringBuffer.append("Image: "+ imgValue.length+ " || "+imgValue.toString()+" \n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(Main_Menu.this);
                builder.setCancelable(true);
                builder.setMessage(stringBuffer.toString());
                builder.show();
            }
        });
    }


/////////////////////////DO NOT ERASE PLZ///////////////////////////////////////
    public TextView ViewIriterate(int txt) { final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);final TextView textView = new TextView(this);textView.setLayoutParams(lparams);textView.setText("Module " + txt+" ");String con = String.valueOf(txt);textView.setContentDescription(con);return textView; }
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
                i = new Intent(this,Profile.class);
                startActivity(i);
                return true;
            }
            case R.id.nav_settings:
            {
                i = new Intent(this, User_setting.class);
                startActivity(i);
                return true;

            }
            case R.id.nav_summary:
            {
                i = new Intent(this,SummaryReport.class);
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
