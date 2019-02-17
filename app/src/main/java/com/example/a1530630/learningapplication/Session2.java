package com.example.a1530630.learningapplication;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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
import java.util.ArrayList;
import java.util.Locale;


public class Session2 extends AppCompatActivity  {

    private ImageButton playbtn,nextbtn,backbtn,homebtn;
    public SoundPool soundPool;
    private Intent intent;
    public int[] sm;
    public int counter,countAttempt; //variable to convert to string as we click next  or previous
    public String aud,mod,les; //aud is the word in the lesson of 0-4 due to array, mod is the module number, les is the lesson#
    public ImageView CorrectOn,CorrectOff,IncorrectOn,IncorrectOff,img;
    public TextView scoreCount,modRes;
    public MediaPlayer mediaPlayer;
    public int score,userCon,modCon,lesCon;
    public float Total,oldScore;
    public long ModResID;
    Bitmap bitmap;
    int count;
    int imgcount;
    ArrayList<byte[]> stuff;
    TextToSpeech textToSpeech;

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
        score = intent.getIntExtra("Score",score); //current score
        modCon = Integer.parseInt(mod);

        CorrectOn = (ImageView)findViewById(R.id.onCorrect);CorrectOff =(ImageView)findViewById(R.id.offCorrect);
        IncorrectOff = (ImageView)findViewById(R.id.offIncorrect);IncorrectOn = (ImageView)findViewById(R.id.onIncorrect);


        String scoreString = String.valueOf(score);
        scoreCount = (TextView)findViewById(R.id.scoreView);

        Total = Float.parseFloat(scoreString);

        imgcount = intent.getIntExtra("Image",imgcount);

        nextbtn = (ImageButton) findViewById(R.id.NextButton);nextbtn.setEnabled(false); //disabled until passed
       // backbtn = (ImageButton) findViewById(R.id.PreviousButton); //back button
        homebtn = (ImageButton)findViewById(R.id.HomeButton); //home button
        playbtn = (ImageButton) findViewById(R.id.PlayButton2);

        //static audio file
        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this, R.raw.aud1);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(false);
        //mediaPlayer.start();


        img = (ImageView)findViewById(R.id.ImageView);
        Cursor cursor = db.getImageSession(modCon,getLesson(les));
        //cycle through database for images and add to arraylist
        if(cursor.moveToFirst())
        {
            stuff = new ArrayList<>();
            do
            {
                byte[] img = cursor.getBlob(4);
                count = cursor.getCount();
                byte[] imgstuff = img;
                stuff.add(imgstuff);
                Log.i("Img data", String.valueOf(img.length));
            }while(cursor.moveToNext());
        }

        bitmap = BitmapFactory.decodeByteArray(stuff.get(imgcount),0,stuff.get(imgcount).length);
        img.setImageBitmap(bitmap);

        //hide back button if start at word 1
//        if(imgcount ==0) { backbtn.setVisibility(View.INVISIBLE); }
//        else { backbtn.setVisibility(View.VISIBLE);}

        scoreCount.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        scoreCount.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite));
        scoreCount.setText("Score: "+scoreString+"/"+count);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.FRANCE);
                    String whatisthis ="qu'est-ce que c'est";
                    //textToSpeech.speak(whatisthis,TextToSpeech.QUEUE_FLUSH,null);
                }
            }
        });

        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatisthis ="qu'est-ce que c'est";
                textToSpeech.speak(whatisthis,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        //configureSounds(les,mod);
        //initiliazeFiles();
        nextFile();
        previousFile();
        homeButton();
        ClickBox();
        test.setText(aud+" "+modCon+" "+les+" "+getLesson(les));
    }


    public void onPause() {
        if(textToSpeech!=null)
        {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }

    @Override
    public void onBackPressed(){ }

    private void homeButton(){homebtn.setOnClickListener(goHome);}
    private View.OnClickListener goHome = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showQuit(view);
        }
    };

    //next lesson
    private void nextFile() { nextbtn.setOnClickListener(nextOne); }
    private View.OnClickListener nextOne = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(imgcount == (count-1))
            {
                if(CorrectOn.getVisibility() == View.VISIBLE)
                { Total = Total + 1; } else { Total = Total +0; }

                Cursor cursor = db.getModuleResID(userCon,modCon);
                if(cursor.moveToFirst()) { ModResID = cursor.getInt(cursor.getColumnIndex(Module_Results.MODULE_RESULT_COLUMN_MODULE_RES_ID)); }

                Cursor getCurrentScore = db.getScore(ModResID);
                if(getCurrentScore.moveToFirst())
                { oldScore = getCurrentScore.getFloat(getCurrentScore.getColumnIndex(les)); }

                Total = (Total/count)*100;
                if(Total > oldScore && db.TestSet(ModResID,Total,les,modCon))
                {
                    db.updateTrack(userCon,modCon);
                    showEnd(view);
                    Toast.makeText(getApplicationContext(), " - "+Total, Toast.LENGTH_LONG).show();
                }
                else
                {
                    db.updateTrack(userCon,modCon);
                    showEnd(view);
                    Toast.makeText(getApplicationContext(), " - "+Total, Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Intent i = new Intent(getApplicationContext(), Session2.class);
                counter = Integer.parseInt(aud);
                counter = counter+1;
                if(CorrectOn.getVisibility() == View.VISIBLE)
                {
                    score = score +1;
                }
                else score = score +0;
                imgcount = imgcount+1;
                String pass = String.valueOf(counter);
                i.putExtra("Image",imgcount);
                i.putExtra("Score",score);
                i.putExtra("Audio", pass);
                i.putExtra("Module",mod);
                i.putExtra("Lesson",les);
                i.putExtra("Score",score);
                startActivity(i);
            }
        }
    };

    //previous lesson
    private void previousFile(){ /*backbtn.setOnClickListener(backOne); */}
    private View.OnClickListener backOne = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), Session2.class);
            counter = Integer.parseInt(aud);
            counter = counter-1;
            imgcount = imgcount-1;
            String pass = String.valueOf(counter);
            i.putExtra("Image",imgcount);
            i.putExtra("Score",score);
            i.putExtra("Audio", pass);
            i.putExtra("Module",mod);
            i.putExtra("Lesson",les);
            i.putExtra("Score",score);
            startActivity(i);
        }
    };

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

    private int getLesson(String lesson) //return int of lesson number
    {
        int les=0;
        if(lesson.equals("Lesson1")){ les =1;}
        else if (lesson.equals("Lesson2")){ les =2;}
        else if (lesson.equals("Lesson3")){ les =3;}
        else if (lesson.equals("Lesson4")){ les =4;}
        else if (lesson.equals("Lesson5")){ les =5;}
        return les;
    }

    private void showEnd(View view) //show popup when user reaches end
    {
        Dialog ending = new Dialog(Session2.this);
        ending.setContentView(R.layout.end_lesson);
        ImageButton but = (ImageButton) ending.findViewById(R.id.EndingHomeButton);
        ImageButton restart = (ImageButton) ending.findViewById(R.id.RestartButton);
        TextView sc = (TextView)ending.findViewById(R.id.Scoring);
        int convert = (int)Total;
        sc.setText("You scored " +convert +"%");
        ending.show();

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Main_Menu.class);
                startActivity(i);
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Session2.class);
                i.putExtra("Image",0);
                i.putExtra("Score",0);
                i.putExtra("Audio", String.valueOf(0));
                i.putExtra("Module",mod);
                i.putExtra("Lesson",les);
                i.putExtra("Score",0);
                startActivity(i);
            }
        });
    }

    private void showQuit(View view)//shows popup to check if they want to quit, will save current score of where they quit
    {
        final Dialog quit = new Dialog(Session2.this);
        quit.setContentView(R.layout.end_session);
        Button no = (Button)quit.findViewById(R.id.quitNo);
        Button yes = (Button)quit.findViewById(R.id.quitYes);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quit.dismiss();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Main_Menu.class);
                if(imgcount == (count-1) && CorrectOn.getVisibility() == View.VISIBLE)
                { Total = Total + 1; } else { Total = Total +0; }

                Cursor cursor = db.getModuleResID(userCon,modCon);
                if(cursor.moveToFirst()) { ModResID = cursor.getInt(cursor.getColumnIndex(Module_Results.MODULE_RESULT_COLUMN_MODULE_RES_ID)); }

                Cursor getCurrentScore = db.getScore(ModResID);
                if(getCurrentScore.moveToFirst())
                { oldScore = getCurrentScore.getFloat(getCurrentScore.getColumnIndex(les)); }

                Total = (Total/count)*100;
                if(Total > oldScore && db.TestSet(ModResID,Total,les,modCon))
                {
                    db.updateTrack(userCon,modCon);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), " - "+Total, Toast.LENGTH_LONG).show();
                }
                else
                {
                    db.updateTrack(userCon,modCon);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), " - "+Total, Toast.LENGTH_LONG).show();
                }
            }
        });

        quit.show();
    }

   /* private void initiliazeFiles() { playbtn.setOnClickListener(playsound); }

    //listener for play button
    private View.OnClickListener playsound = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String soundname = (String) view.getContentDescription().toString();counter = Integer.parseInt(aud);
            if(soundname.contentEquals("play1")) { soundPool.play(sm[counter],1,1,1,0,1.0f); }
        }
    };
    private void configureSounds(String lesson,String module)
    {
        soundPool = new SoundPool(1,AudioManager.STREAM_MUSIC,0);sm = new int[5];
        if(lesson.equals("Lesson1") && module.equals("1")) { sm[0] = soundPool.load(this, R.raw.mod1les1w1,1);sm[1] = soundPool.load(this, R.raw.aud2,1);sm[2] = soundPool.load(this, R.raw.aud3,1);sm[3] = soundPool.load(this, R.raw.aud1,1);sm[4] = soundPool.load(this, R.raw.aud2,1); }
        else if(lesson.equals("Lesson1")&& module.equals("2")) { sm[0] = soundPool.load(this, R.raw.aud6,1);sm[1] = soundPool.load(this, R.raw.aud7,1);sm[2] = soundPool.load(this, R.raw.aud1,1);sm[3] = soundPool.load(this, R.raw.aud2,1);sm[4] = soundPool.load(this, R.raw.aud3,1);}
    }*/
}
