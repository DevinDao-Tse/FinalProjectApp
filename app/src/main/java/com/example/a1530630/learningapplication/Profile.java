package com.example.a1530630.learningapplication;

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

public class Profile extends Main_Menu {

    SQLiteManage db;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        EditText Fullname = findViewById(R.id.UserFull);
        EditText Username = findViewById(R.id.UserName);
        EditText Password = findViewById(R.id.UserPass);
        EditText Email = findViewById(R.id.UserEmail);
        TextView UserID = findViewById(R.id.UserID);

        db = new SQLiteManage(this);
        SharedPreferences settings = getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);
        String userName = settings.getString("Username",null);
        String fullName = settings.getString("FullName",null);
        String passWord = settings.getString("Password",null);
        String email = settings.getString("Email",null);
        Integer id = settings.getInt("UserID",0);
        String showID = String.valueOf(id);

        Fullname.setText(fullName);
        Username.setText(userName);
        Password.setText(passWord);
        Email.setText(email);
        UserID.setText(showID);

    }

    public void UpdateUser(View view)
    {
        SharedPreferences settings = getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        Integer id = settings.getInt("UserID",0);
        editor.clear();
        editor.commit();

        EditText Fullname = findViewById(R.id.UserFull);
        EditText Username = findViewById(R.id.UserName);
        EditText Password = findViewById(R.id.UserPass);
        EditText Email = findViewById(R.id.UserEmail);

        String userName = Fullname.getText().toString();
        String fullName = Username.getText().toString();
        String passWord = Password.getText().toString();
        String email = Email.getText().toString();

        try
        {
            User user = new User();
            user.setFullName(fullName);
            user.setUsername(userName);
            user.setPassword(passWord);
            user.setEmail(email);


            db.UpdateProfile(user, id);

            editor.putInt("UserID",id);
            editor.putString("Username",user.getUsername());
            editor.putString("Password",user.getPassword());
            editor.putString("Email",user.getEmail());
            editor.putString("FullName",user.getFullName());
            editor.commit();

            Toast.makeText(this, "Profile Updated",Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), Main_Menu.class);
            startActivity(i);
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
