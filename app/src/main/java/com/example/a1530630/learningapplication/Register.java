package com.example.a1530630.learningapplication;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.User;

import java.io.IOException;
import java.net.HttpURLConnection;

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
            EditText FullName =  findViewById(R.id.fullNameText);
            EditText Username = findViewById(R.id.nameText);
            EditText Email = findViewById(R.id.emailText);
            EditText Password = findViewById(R.id.passwordText);
            EditText ConfirmPassword = findViewById(R.id.confirmPass);

            String name = FullName.getText().toString();
            String user = Username.getText().toString();
            String email = Email.getText().toString();
            String password = Password.getText().toString();
            String confirmpassword = ConfirmPassword.getText().toString();

            if(name.isEmpty() || user.isEmpty()  || email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty())
            {
                Toast.makeText(getApplicationContext(),"Enter user details",Toast.LENGTH_LONG).show();
            }

            if(!password.equalsIgnoreCase(confirmpassword))
            {
                Toast.makeText(getApplicationContext(),"Passwords are not the same",Toast.LENGTH_LONG).show();
            }
            else
                {
                    User newUser = new User(user,password,name,email);

                    if(newUser != null) {
                        db.addNewUser(newUser);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("UserID", newUser.getUserID());
                        editor.putString("Username", user);
                        editor.putString("Password", password);
                        editor.putString("Email", email);
                        editor.putString("FullName", name);
                        editor.commit();

                        Toast.makeText(this, "User Account created", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), Main_Menu.class);
                        startActivity(i);
                    }
            }

        }
        catch(Exception e)
        {
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


}
