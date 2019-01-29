package com.example.a1530630.learningapplication;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.Module_Results;

import org.w3c.dom.Text;

import java.text.DecimalFormat;


public class Session2 extends AppCompatActivity  {

    private ImageButton playbtn,nextbtn,backbtn,homebtn;
    public SoundPool soundPool;
    private Intent intent;
    public int[] sm;
    public int counter,countAttempt; //variable to convert to string as we click next  or previous
    public String aud,mod,les; //aud is the word in the lesson of 0-4 due to array, mod is the module number, les is the lesson#
    public ImageView CorrectOn,CorrectOff,IncorrectOn,IncorrectOff;
   //  public ImageView imgWord = (ImageView)findViewById(R.id.ImageView);
    public TextView scoreCount,modRes;
    public MediaPlayer mediaPlayer;
    public int score,userCon,modCon;
    public float Total;
    public long ModResID;


    SharedPreferences sharedPreferences;

    SQLiteManage db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session2);
        getSupportActionBar().hide();
        db = new SQLiteManage(this);
        sharedPreferences = this.getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);
        userCon = sharedPreferences.getInt("UserID",0);

        TextView test = findViewById(R.id.textViewTest);
        intent = getIntent();
        aud = intent.getStringExtra("Audio"); //passing audio#
        mod = intent.getStringExtra("Module"); //passing module #
        les = intent.getStringExtra("Lesson"); //passing lesson#
        modCon = Integer.parseInt(mod);



        CorrectOn = (ImageView)findViewById(R.id.onCorrect);CorrectOff =(ImageView)findViewById(R.id.offCorrect);
        IncorrectOff = (ImageView)findViewById(R.id.offIncorrect);IncorrectOn = (ImageView)findViewById(R.id.onIncorrect);

        score = intent.getIntExtra("Score",score);
        String scoreString = String.valueOf(score);
        scoreCount = (TextView)findViewById(R.id.scoreView);
        scoreCount.setText(scoreString);
        Total = Float.parseFloat(scoreString);

        nextbtn = (ImageButton) findViewById(R.id.NextButton);nextbtn.setEnabled(false); //disabled until passed
        backbtn = (ImageButton) findViewById(R.id.PreviousButton); //back button
        homebtn = (ImageButton)findViewById(R.id.HomeButton); //home button
        playbtn = (ImageButton) findViewById(R.id.PlayButton2);

        //hide back button if start at word 1
        if(aud.equalsIgnoreCase("0")) { backbtn.setVisibility(View.INVISIBLE); }
        else { backbtn.setVisibility(View.VISIBLE);}

        //hide next button if start at last word
        if(aud.equalsIgnoreCase("4")){ nextbtn.setVisibility(View.INVISIBLE);}
        else {nextbtn.setVisibility(View.VISIBLE);}

        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this, R.raw.aud1);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(false);
        mediaPlayer.start();

        configureSounds(les,mod);
        initiliazeFiles();
        nextFile();
        previousFile();
        homeButton();//initiate();
        ClickBox();
        test.setText(aud+" "+modCon+" "+les);
    }

    @Override
    public void onBackPressed(){
        //super.onBackPressed(); //comment out if you want back button to do something
    }

    private void initiliazeFiles() { playbtn.setOnClickListener(playsound); }

    //listener for play button
    private View.OnClickListener playsound = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String soundname = (String) view.getContentDescription().toString();
            counter = Integer.parseInt(aud);
            if(soundname.contentEquals("play1"))
            {
                soundPool.play(sm[counter],1,1,1,0,1.0f);
            }
        }
    };


    private void homeButton(){homebtn.setOnClickListener(goHome);}
    private View.OnClickListener goHome = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), Main_Menu.class);
            if(aud.equals("4") && CorrectOn.getVisibility() == View.VISIBLE)
            { Total = Total + 1; } else { Total = Total +0; }

            Cursor cursor = db.getModuleResID(userCon,modCon);
            if(cursor.moveToFirst())
            {
                ModResID = cursor.getInt(cursor.getColumnIndex(Module_Results.MODULE_RESULT_COLUMN_MODULE_RES_ID));
            }

            Total = (Total/5)*100;
            if(db.TestSet(ModResID,Total,les,modCon))
            {
                    db.updateTrack(userCon,modCon);

                    startActivity(i);
                    Toast.makeText(getApplicationContext(), " - "+Total+" - " + " \n- " , Toast.LENGTH_LONG).show();
           }
        }
    };

    //next lesson
    private void nextFile() { nextbtn.setOnClickListener(nextOne); }
    private View.OnClickListener nextOne = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), Session2.class);
            counter = Integer.parseInt(aud);
            counter = counter+1;
            if(CorrectOn.getVisibility() == View.VISIBLE)
            {
                score = score +1;
            }
            else score = score +0;
            String pass = String.valueOf(counter);
            //i.putExtra("ModResID", ModResID);
            i.putExtra("Score",score);
            i.putExtra("Audio", pass);
            i.putExtra("Module",mod);
            i.putExtra("Lesson",les);
            i.putExtra("Score",score);
            startActivity(i);
        }
    };

    //previous lesson
    private void previousFile(){ backbtn.setOnClickListener(backOne); }
    private View.OnClickListener backOne = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), Session2.class);
            counter = Integer.parseInt(aud);
            counter = counter-1;
            String pass = String.valueOf(counter);
            i.putExtra("Audio", pass);
            i.putExtra("Module",mod);
            i.putExtra("Lesson",les);
            startActivity(i);
        }
    };

    private void configureSounds(String lesson,String module)
    {
        soundPool = new SoundPool(1,AudioManager.STREAM_MUSIC,0);
        sm = new int[5];
        if(lesson.equals("Lesson1") && module.equals("1"))
        {
            sm[0] = soundPool.load(this, R.raw.mod1les1w1,1);
            sm[1] = soundPool.load(this, R.raw.aud2,1);
            sm[2] = soundPool.load(this, R.raw.aud3,1);
            sm[3] = soundPool.load(this, R.raw.aud1,1);
            sm[4] = soundPool.load(this, R.raw.aud2,1);

        }
        else if(lesson.equals("Lesson1")&& module.equals("2"))
        {
            sm[0] = soundPool.load(this, R.raw.aud6,1);
            sm[1] = soundPool.load(this, R.raw.aud7,1);
            sm[2] = soundPool.load(this, R.raw.aud1,1);
            sm[3] = soundPool.load(this, R.raw.aud2,1);
            sm[4] = soundPool.load(this, R.raw.aud3,1);
        }
    }

    private void ClickBox()
    {
        CorrectOn.setOnClickListener(chooseBox);
        CorrectOff.setOnClickListener(chooseBox);
        IncorrectOff.setOnClickListener(chooseBox);
        IncorrectOn.setOnClickListener(chooseBox);
    }
    private View.OnClickListener chooseBox = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId())
            {
                case R.id.offCorrect: //checked correct box
                    CorrectOn.setVisibility(View.VISIBLE); //checkmark on right one show
                    nextbtn.setEnabled(true);
                    CorrectOff.setVisibility(View.INVISIBLE);
                    IncorrectOn.setVisibility(View.INVISIBLE);
                    IncorrectOff.setVisibility(View.VISIBLE);

                    break;
                case R.id.offIncorrect:
                    IncorrectOn.setVisibility(View.VISIBLE);
                    nextbtn.setEnabled(true);
                    IncorrectOff.setVisibility(View.INVISIBLE);
                    CorrectOn.setVisibility(View.INVISIBLE);
                    CorrectOff.setVisibility(View.VISIBLE);

                    break;
            }
        }
    };

}
