package com.example.a1530630.learningapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.User;

public class Profile extends Main_Menu implements NavigationView.OnNavigationItemSelectedListener {

    SQLiteManage db;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);

        //inputs
        EditText Fullname = findViewById(R.id.UserFull);
        EditText Username = findViewById(R.id.UserName);
        EditText Password = findViewById(R.id.UserPass);
        EditText Email = findViewById(R.id.UserEmail);
        TextView UserID = findViewById(R.id.UserID);

        db = new SQLiteManage(this);
        SharedPreferences settings = getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);

        //converted inputs
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

        t = new ActionBarDrawerToggle(this, dl,R.string.nav_open, R.string.nav_close);
        dl.addDrawerListener(t);

        t.syncState(); //getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dl.addDrawerListener(new DrawerLayout.DrawerListener()
        {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) { }
            @Override
            public void onDrawerOpened(@NonNull View view)
            {
                SharedPreferences settings = getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);
                String userName = settings.getString("Username",null);
                TextView user = findViewById(R.id.nav_header_textView);
                user.setText(userName);
            }
            @Override
            public void onDrawerClosed(@NonNull View view) {}
            @Override
            public void onDrawerStateChanged(int i) {}
        });

        NavigationView nv = findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(this);

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(t.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent i;
        switch (menuItem.getItemId())
        {
            case R.id.nav_menu:
            {
                i = new Intent(this,Main_Menu.class);
                startActivity(i);
                return true;
            }
            case R.id.nav_profile:
            {
                i = new Intent(this, Profile.class);
                startActivity(i);
                return true;
            }
            case R.id.nav_settings:
            {

                return true;
            }
            case R.id.nav_exit:
            {
                SharedPreferences settings = getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.commit();
                i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
                return true;
            }
        }

        return true;
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
