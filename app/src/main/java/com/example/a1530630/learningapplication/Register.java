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
import com.example.a1530630.learningapplication.Models.Module_Results;
import com.example.a1530630.learningapplication.Models.User;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SQLiteManage db;
   String EMAIL_PATTERN = "[@#$%^&+=]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new SQLiteManage(this);
        getSupportActionBar().hide();
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

            if(!name.isEmpty() || !user.isEmpty()  || !email.isEmpty() || !password.isEmpty() || !confirmpassword.isEmpty())
            {
                if(!email.matches(EMAIL_PATTERN))
                {
                    if(password.equalsIgnoreCase(confirmpassword))
                    {
                        MD5 hash = new MD5();
                        User newUser = new User(user,hash.hashPass(password),name,email);

                        if(!db.User_Exist(email, user))
                        {
                            if(newUser != null) {
                                db.addNewUser(newUser);
                                hash.hashPass(password);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("UserID", newUser.getUserID());
                                editor.putString("Username", user);
                                editor.putString("Password", hash.hashPass(password));
                                editor.putString("Email", email);
                                editor.putString("FullName", name);
                                editor.putBoolean("New User",true);
                                editor.commit();

                                Toast.makeText(this, "User Account created\n"+hash.hashPass(password), Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), Main_Menu.class);
                                startActivity(i);
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Account already exist",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Passwords are not the same",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Enter user details",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e)
        {
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


}
