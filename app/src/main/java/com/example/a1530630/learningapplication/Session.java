package com.example.a1530630.learningapplication;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
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

    private ImageButton playbtn;
    private SoundPool soundPool;
    private SparseIntArray soundmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        TextView test = findViewById(R.id.textViewTest);
        Intent intent = getIntent();

        String aud = intent.getStringExtra("Audio1");
        test.setText(aud);
        configureSounds();
        initiliazeFiles();
    }

    private void configureSounds()
    {
        soundPool = new SoundPool(1,AudioManager.STREAM_MUSIC,0);
        soundmap = new SparseIntArray(1);
        soundmap.put(1,soundPool.load(this,R.raw.aud1,1));
    }

    private void initiliazeFiles()
    {
        playbtn = (ImageButton) findViewById(R.id.PlayButton);
        playbtn.setOnClickListener(playsound);

    }
    private View.OnClickListener playsound = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String soundname = (String) view.getContentDescription();
            if(soundname.contentEquals("play1"))
            {
                soundPool.play(1,1,1,1,0,1.0f);
            }
        }
    };
}
