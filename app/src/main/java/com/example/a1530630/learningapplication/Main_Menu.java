package com.example.a1530630.learningapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a1530630.learningapplication.Database.SQLiteManage;

public class Main_Menu extends AppCompatActivity {

    SQLiteManage db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);

        db = new SQLiteManage(this);

        Button logout = findViewById(R.id.logoffBtn);
        Button profile = findViewById(R.id.ProfileBtn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Profile.class);
                startActivity(i);
            }
        });
    }
}
