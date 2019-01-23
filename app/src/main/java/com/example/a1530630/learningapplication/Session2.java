package com.example.a1530630.learningapplication;


import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class Session2 extends AppCompatActivity  {

    private ImageButton playbtn,nextbtn,backbtn,homebtn;
    public SoundPool soundPool;
    private Intent intent;
    public int[] sm;
    public int counter,countAttempt; //variable to convert to string as we click next  or previous
    public String aud,mod,les; //aud is the word in the lesson of 0-4 due to array, mod is the module number, les is the lesson#

    public MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session2);
        getSupportActionBar().hide();


        TextView test = findViewById(R.id.textViewTest);
        intent = getIntent();
        aud = intent.getStringExtra("Audio"); //passing audio#
        mod = intent.getStringExtra("Module"); //passing module #
        les = intent.getStringExtra("Lesson"); //passing lesson#

        nextbtn = (ImageButton) findViewById(R.id.NextButton); //next button
        nextbtn.setEnabled(true); //disabled until passed
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
        test.setText(aud+" "+mod+" "+les);
    }


    private void configureSounds(String lesson,String module)
    {
        soundPool = new SoundPool(1,AudioManager.STREAM_MUSIC,0);
        sm = new int[5];
        if(lesson.equals("LessonOne") && module.equals("1"))
        {
            sm[0] = soundPool.load(this, R.raw.mod1les1w1,1);
            sm[1] = soundPool.load(this, R.raw.aud2,1);
            sm[2] = soundPool.load(this, R.raw.aud3,1);
            sm[3] = soundPool.load(this, R.raw.aud1,1);
            sm[4] = soundPool.load(this, R.raw.aud2,1);

        }
        else if(lesson.equals("LessonOne")&& module.equals("2"))
        {
            sm[0] = soundPool.load(this, R.raw.aud6,1);
            sm[1] = soundPool.load(this, R.raw.aud7,1);
            sm[2] = soundPool.load(this, R.raw.aud1,1);
            sm[3] = soundPool.load(this, R.raw.aud2,1);
            sm[4] = soundPool.load(this, R.raw.aud3,1);
        }
    }
    //plays sound based on module/lesson
    private void setPlaysound(int num)
    {
        soundPool.play(sm[num],1,1,1,0,1.0f);
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
            startActivity(i);
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
            String pass = String.valueOf(counter);
            i.putExtra("Audio", pass);
            i.putExtra("Module",mod);
            i.putExtra("Lesson",les);
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


}
