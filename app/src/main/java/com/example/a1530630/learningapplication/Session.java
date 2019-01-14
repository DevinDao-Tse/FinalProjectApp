package com.example.a1530630.learningapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Session extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        TextView test = findViewById(R.id.textViewTest);
        Intent intent = getIntent();

        String aud = intent.getStringExtra("Audio1");
        test.setText(aud);

    }
}
