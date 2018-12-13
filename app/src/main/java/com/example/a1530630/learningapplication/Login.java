package com.example.a1530630.learningapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

        TextView RegisterView = findViewById(R.id.RegisterView);

        RegisterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
            }
        });
    }

    public void LoginBtn(View view)
    {
        EditText LoginUser = findViewById(R.id.LoginUser);
        EditText LoginPassword = findViewById(R.id.LoginPassword);

        String username = LoginUser.getText().toString();
        String password = LoginPassword.getText().toString();
        sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        try
        {
            if(username.isEmpty() || password.isEmpty())
            {
                Toast.makeText(getApplicationContext(),"Enter login credentials", Toast.LENGTH_LONG).show();
            }
            else
                {

                    if(db.Login(username,password))
                    {
                        User user = new User();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //editor.putInt("UserID",user.getUserID());
                        editor.putString("Username",username);
                        editor.putString("Password",password);
                        //editor.putString("Email",user.getEmail());
                        //editor.putString("FullName",user.getFullName());
                        editor.commit();

                        Toast.makeText(this, "Logged In",Toast.LENGTH_LONG).show();
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
