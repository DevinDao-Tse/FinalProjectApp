package com.example.a1530630.learningapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.User;

public class Login extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public static final String MyPreferences ="User";
    SQLiteManage db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new SQLiteManage(this);
    }

    public void LoginBtn(View view)
    {
        sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        try
        {


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("UserID",0);
            editor.putString("Username","");
            editor.putString("Password","");
            editor.putString("Email","");
            editor.putString("FirstName","");
            editor.putString("LastName","");
            editor.commit();

            Toast.makeText(this, "User Account created",Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), Main_Menu.class);
            startActivity(i);
        }
        catch(Exception e)
        {
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
