package com.example.a1530630.learningapplication.Audio;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.widget.ImageButton;

import com.example.a1530630.learningapplication.R;

public class Audio_files
{
    private SoundPool soundPool;
    int[] sm;

    public Audio_files(){}

    public int[] configure(Context now, String lesson)
    {
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        sm = new int[5];
        if(lesson.equalsIgnoreCase("Lesson1"))
        {
            sm[0] = soundPool.load(now, R.raw.aud1,1);
            sm[1] = soundPool.load(now, R.raw.aud2,1);
            sm[2] = soundPool.load(now, R.raw.aud3,1);
        }
        else if(lesson.equalsIgnoreCase("Lesson2"))
        {
            sm[0] = soundPool.load(now, R.raw.aud6,1);
            sm[1] = soundPool.load(now, R.raw.aud7,1);
        }
        return sm;
    }
}
