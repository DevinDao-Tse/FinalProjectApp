package com.example.a1530630.learningapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.User;
import com.example.a1530630.learningapplication.Models.User_Track;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Main_Menu implements NavigationView.OnNavigationItemSelectedListener {

    SQLiteManage db;
    SharedPreferences sharedPreferences;
    float total;
    int res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dl  = (DrawerLayout) findViewById(R.id.drawer_layout);

        //TextView of username
        TextView Username = findViewById(R.id.profileUserName);

        db = new SQLiteManage(this);
        SharedPreferences settings = getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);

        //See the username
        String userName = settings.getString("Username",null);
        Username.setText(userName);
        Description description = new Description();
        description.setText("");

        PieChart pieChart = (PieChart)findViewById(R.id.userPieChart);
        //pieChart.setUsePercentValues(true);
        pieChart.setDescription(description);

        int id = settings.getInt("UserID",0);

        Cursor cursor1 = db.trackUpdateUser(id);
        Cursor cursor2 = db.trackModule();

        if(cursor1.moveToFirst())
        {
            do{
                res += cursor1.getInt(cursor1.getColumnIndex(User_Track.USER_TRACK_COLUMN_RESULT));
            }while(cursor1.moveToNext());
        }


        if(cursor2.moveToFirst())
        {
            total =  cursor2.getCount()*100;
        }
        else
            {
                total =0;
            }

        int totalPercent = (int)total;
        float resPercent = ((float)res/total)*100;
        int sub= (100-(int)resPercent);

        List<PieEntry> value = new ArrayList<>();
        value.add(new PieEntry(resPercent,"user"));
        value.add(new PieEntry(sub,"incomplete"));

        PieDataSet pieDataSet = new PieDataSet(value,"Progression");
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);








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
            //Goes to user setting
            public void profileEdit(View view)
            {
                Intent i = new Intent(this, User_setting.class);
                startActivity(i);
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
                i = new Intent(this,Profile.class);
                startActivity(i);
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

}
