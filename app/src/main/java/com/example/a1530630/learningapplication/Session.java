package com.example.a1530630.learningapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

public class Session extends AppCompatActivity {

    private ImageButton playbtn,recordbtn,nextbtn;
    private SoundPool soundPool;
    private Intent intent;
    int[] sm;
    private SparseIntArray soundmap;
    public int counter =0;

    private MediaRecorder mediaRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        TextView test = findViewById(R.id.textViewTest);
        intent = getIntent();
        String aud = intent.getStringExtra("Audio");

        recordbtn = (ImageButton) findViewById(R.id.RecordBtn); //recordbtn.setImageResource(R.drawable.ic_settings);
        nextbtn = (ImageButton) findViewById(R.id.NextButton);

        /*if(!aud.equalsIgnoreCase("0"))
        {
            nextbtn.setVisibility(View.INVISIBLE);
        }
        else
        {
            nextbtn.setVisibility(View.VISIBLE);
        }*/

        test.setText(aud);
        configureSounds();
        initiliazeFiles();
        nextFile();

    }

    private void StartRecord()
    {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
    }

    private void configureSounds()
    {
        soundPool = new SoundPool(1,AudioManager.STREAM_MUSIC,0);
        sm = new int[3];
        sm[0] = soundPool.load(this, R.raw.aud1,1);
        sm[1] = soundPool.load(this, R.raw.aud2,1);
        sm[2] = soundPool.load(this, R.raw.aud3,1);
        //soundmap = new SparseIntArray(1);
        //soundmap.put(1,soundPool.load(this,R.raw.aud1,1));
    }

    private void initiliazeFiles()
    {
        playbtn = (ImageButton) findViewById(R.id.PlayButton);
        playbtn.setOnClickListener(playsound);
    }

    //next lesson
    private void nextFile()
    {
        nextbtn.setOnClickListener(nextOne);
    }

    //listener for going to next lesson
    private View.OnClickListener nextOne = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), Session.class);
            counter = counter+1;
            String pass = String.valueOf(counter);
            i.putExtra("Audio", pass);
            startActivity(i);
        }
    };

    //plays sound based on module/lesson
    private void setPlaysound(int num)
    {
        soundPool.play(sm[num],1,1,1,0,1.0f);
    }

    //listener for play button
    private View.OnClickListener playsound = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            intent = getIntent();
            String aud = intent.getStringExtra("Audio");
            String soundname = (String) view.getContentDescription().toString();
             counter = Integer.parseInt(aud);
            if(soundname.contentEquals("play1"))
            {
                setPlaysound(counter);
            }
        }
    };
}
