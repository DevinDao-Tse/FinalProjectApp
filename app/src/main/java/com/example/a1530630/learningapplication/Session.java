package com.example.a1530630.learningapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import com.example.a1530630.learningapplication.Audio.Audio_files;

import java.io.File;
import java.io.IOException;

public class Session extends AppCompatActivity {

    private ImageButton playbtn,nextbtn,homebtn;
    private Button recordbtn;
    public SoundPool soundPool;
    private Intent intent;
    public int[] sm,sm2;
    public int counter;
    public int wordCounter =0;
    public String aud,mod,les;
    private TextView see;

    private String FILE;
    private MediaRecorder mediaRecorder;
    private MediaPlayer play;

    Audio_files files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        TextView test = findViewById(R.id.textViewTest);
        intent = getIntent();
        aud = intent.getStringExtra("Audio");
        mod = intent.getStringExtra("Module");
        les = intent.getStringExtra("Lesson");

        FILE = Environment.getExternalStorageState()+"/tempRecord.3gpp";

        recordbtn = (Button) findViewById(R.id.RecordBtn); //recordbtn.setImageResource(R.drawable.ic_settings);
        nextbtn = (ImageButton) findViewById(R.id.NextButton);
        homebtn = (ImageButton)findViewById(R.id.HomeButton);

        configureSounds(les);
        initiliazeFiles();
        nextFile();
        homeButton();
        initiate();
        test.setText(aud+" "+mod+" "+les);
    }


   private void configureSounds(String lesson)
    {
        soundPool = new SoundPool(1,AudioManager.STREAM_MUSIC,0);
        sm = new int[5];
        if(lesson.equals("Lesson1"))
        {
            sm[0] = soundPool.load(this, R.raw.aud1,1);
            sm[1] = soundPool.load(this, R.raw.aud2,1);
            sm[2] = soundPool.load(this, R.raw.aud3,1);
            sm[3] = soundPool.load(this, R.raw.aud1,1);
            sm[4] = soundPool.load(this, R.raw.aud2,1);

        }
        else if(lesson.equals("Lesson2"))
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

    private void initiliazeFiles()
    {
        playbtn = (ImageButton) findViewById(R.id.PlayButton);
        playbtn.setOnClickListener(playsound);
    }

    //listener for play button
    private View.OnClickListener playsound = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            aud = intent.getStringExtra("Audio");
            String soundname = (String) view.getContentDescription().toString();
            counter = Integer.parseInt(aud);
            if(soundname.contentEquals("play1"))
            {
                soundPool.play(sm[counter],1,1,1,0,1.0f);
            }
        }
    };


    private void initiate()
    {
        recordbtn = (Button) findViewById(R.id.RecordBtn);
        recordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                see = (TextView)findViewById(R.id.TxtRecord);
                if(recordbtn.getText().toString().equals(("Record")))
                {
                    try {
                        StartRecord();
                    }
                    catch (IOException e) { e.printStackTrace(); }
                    see.setText("RECORDING......");
                    recordbtn.setText("End");
                }
                else if(recordbtn.getText().toString().equals("End"))
                {
                    StopRecord();
                    recordbtn.setText("Play");
                    see.setText("");
                }
                else if(recordbtn.getText().toString().equals("Play"))
                {
                    try {
                        startPlayback();
                    }
                    catch (IOException e) { e.printStackTrace(); }
                    recordbtn.setText("Record");
                }
                else{
                    stopPlayBack();
                }
            }
        });
    }


    private void StartRecord() throws IOException {
        if(mediaRecorder!=null){mediaRecorder.release();}

        File fileout = new File(FILE);
        if (fileout != null)
        {
            fileout.delete();
        }
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(FILE);

        mediaRecorder.prepare();
        mediaRecorder.start();
    }
    private void StopRecord()
    {
        mediaRecorder.stop();
        mediaRecorder.release();
    }
    private void startPlayback() throws IOException
    {
        if(play!=null)
        {
            play.stop();
            play.release();
        }
        play = new MediaPlayer();
        play.setDataSource(FILE);
        play.prepare();
        play.start();
        play.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                play.release();
            }
        });
    }
    private void stopPlayBack()
    {
        play.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                play.release();
            }
        });
    }






















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
            Intent i = new Intent(getApplicationContext(), Session.class);
            counter = counter+1;
            String pass = String.valueOf(counter);
            i.putExtra("Audio", pass);
            i.putExtra("Module",mod);
            i.putExtra("Lesson",les);
            startActivity(i);
        }
    };

    //previous lesson
    private void previousFile(){ }



}
