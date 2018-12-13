package com.example.a1530630.learningapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.User;

public class Register extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SQLiteManage db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new SQLiteManage(this);

    }
    public void RegisterUser(View view)
    {
        sharedPreferences = getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);
        try
        {
            User newUser = new User();
            if(newUser != null)
            {
                db.addNewUser(newUser);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("UserID",0);
                editor.putString("Username","");
                editor.putString("Password","");
                editor.putString("Email","");
                editor.putString("FullName","");
                editor.commit();

                Toast.makeText(this, "User Account created",Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), Main_Menu.class);
                startActivity(i);
            }
        }
        catch(Exception e)
        {
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
