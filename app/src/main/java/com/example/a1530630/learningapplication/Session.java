package com.example.a1530630.learningapplication;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.Image;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Environment;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.a1530630.learningapplication.Audio.Audio_files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Session extends AppCompatActivity implements RecognitionListener {


    //https://androstock.com/tutorials/android-speech-to-text-converter-android-studio.html
    //use reference for speech

    private ImageButton playbtn,nextbtn,homebtn;
    private Button recordbtn,stopbtn;
    public SoundPool soundPool;
    private Intent intent;
    public int[] sm;
    public int counter; //variable to convert to string as we click next  or previous
    public String aud,mod,les,wordTest,match; //aud is the word in the lesson of 0-4 due to array, mod is the module number, les is the lesson#
    private TextView see,returnedText;

    private String FILE;
    private MediaRecorder mediaRecorder;
    private MediaPlayer play;

    private ProgressBar progressBar;
    private SpeechRecognizer speech =null;
    private Intent recognizeIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        getSupportActionBar().hide();



        TextView test = findViewById(R.id.textViewTest);
        intent = getIntent();
        aud = intent.getStringExtra("Audio");
        mod = intent.getStringExtra("Module");
        les = intent.getStringExtra("Lesson");
        see = (TextView)findViewById(R.id.TxtRecord);


        FILE = Environment.getExternalStorageState()+"/tempRecord.3gpp";


        nextbtn = (ImageButton) findViewById(R.id.NextButton);
        homebtn = (ImageButton)findViewById(R.id.HomeButton);





        recordbtn = (Button) findViewById(R.id.RecordBtn); //recordbtn.setImageResource(R.drawable.ic_settings);
        stopbtn = (Button)findViewById(R.id.StopBtn); stopbtn.setEnabled(false);

        returnedText = (TextView) findViewById(R.id.textView1);
        returnedText.setTextColor(Color.RED);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        progressBar.setVisibility(View.INVISIBLE);
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizeIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        //recognizeIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        //recognizeIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "fr-CA");
        recognizeIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fr-CA");
        recognizeIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,this.getPackageName());
        recognizeIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizeIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,true);
        recognizeIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 5000);
        recognizeIntent.putExtra("android.speech.extra.DICTATION_MODE", true);

        recordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                speech.startListening(recognizeIntent);
                see.setText("Recording.......");
                recordbtn.setEnabled(false);
                stopbtn.setEnabled(true);

            }
        });

        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.INVISIBLE);
                speech.stopListening();
                see.setText("");
                recordbtn.setEnabled(true);
                stopbtn.setEnabled(false);
            }
        });


        configureSounds(les);
        initiliazeFiles();
        nextFile();
        homeButton();//initiate();
        test.setText(aud+" "+mod+" "+les);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            Log.d("Log", "destroy");
        }

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d("Log", "onBeginningOfSpeech");
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.d("Log", "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.d("Log", "onEndOfSpeech");
        progressBar.setVisibility(View.INVISIBLE);
        recordbtn.setEnabled(true);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d("Log", "FAILED " + errorMessage);
        progressBar.setVisibility(View.INVISIBLE);
        returnedText.setText(errorMessage);
        recordbtn.setEnabled(true);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.d("Log", "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.d("Log", "onPartialResults");
        wordTest = "Table";
        ArrayList<String> matches = arg0.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
         //To get all close matchs
        /*for (String result : matches)
        {text += result + "\n";}*/

        text = matches.get(0); //  Remove this line while uncommenting above codes
        if(text.equalsIgnoreCase(wordTest))
        {
            match= "match";
        }
        else
            {
                match ="does not match";
            }

        returnedText.setText(text+" // "+ match);
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.d("Log", "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.d("Log", "onResults");

    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.d("Log", "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);

    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
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

/*
    private void initiate()
    {
        recordbtn = (Button) findViewById(R.id.RecordBtn); recordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                see = (TextView)findViewById(R.id.TxtRecord);
                if(recordbtn.getText().toString().equals(("Record")))
                {
                    try { StartRecord();}
                    catch (IOException e) { e.printStackTrace(); }
                    see.setText("RECORDING......");recordbtn.setText("End");
                }
                else if(recordbtn.getText().toString().equals("End"))
                {
                    StopRecord(); recordbtn.setText("Play");see.setText("");
                }
                else if(recordbtn.getText().toString().equals("Play"))
                {
                    try {startPlayback();}
                    catch (IOException e) { e.printStackTrace(); }
                    recordbtn.setText("Record");
                }
                else{stopPlayBack();}
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

*/


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
