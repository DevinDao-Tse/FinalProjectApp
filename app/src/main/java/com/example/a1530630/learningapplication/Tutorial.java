package com.example.a1530630.learningapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Tutorial extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotLayout;
    private  TutorialAdapter tutorialAdapter;
    private TextView[] dots;
    private Button next,back,skip;
    private int currentPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        getSupportActionBar().hide();

        viewPager = (ViewPager)findViewById(R.id.sliders);
        dotLayout = (LinearLayout)findViewById(R.id.dotsLinear);

        next = (Button)findViewById(R.id.SlideNext);
        back = (Button)findViewById(R.id.SlideBack);
        skip = (Button)findViewById(R.id.skipBtn);

        tutorialAdapter = new TutorialAdapter(this);
        viewPager.setAdapter(tutorialAdapter);

        addDots(0);
        viewPager.addOnPageChangeListener(viewListener);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(next.getText().toString().equals("FINISH"))
                {
                    //change new user setting to false
                    SharedPreferences settings = getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("New User", false);
                    editor.commit();
                    Intent i = new Intent(getApplicationContext(), Main_Menu.class);
                    startActivity(i);
                }
                else
                viewPager.setCurrentItem(currentPage+1);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(currentPage-1);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("New User", false);
                editor.commit();
                Intent i = new Intent(getApplicationContext(), Main_Menu.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() { }

    public void addDots(int position)
    {
        dots = new TextView[4];
        dotLayout.removeAllViews();
        for(int i =0; i<dots.length;i++)
        {
            dots[i] = new TextView(getApplicationContext());
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.dotsColor));
            dotLayout.addView(dots[i]);
        }
        if(dots.length>0)
        {
            //setting dot to white based on position/page
            dots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPage = position;

            if(position ==0) //starting slider page
            {
                next.setEnabled(true);
                back.setEnabled(false);
                back.setVisibility(View.INVISIBLE);

                next.setText("NEXT");
                back.setText("");
            }
            else if(position == dots.length-1) //ending slider page
            {
                next.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);

                next.setText("FINISH");
                back.setText("BACK");
            }
            else //middle slider pages
                {
                    next.setEnabled(true);
                    back.setEnabled(true);
                    back.setVisibility(View.VISIBLE);

                    next.setText("NEXT");
                    back.setText("BACK");
                }
        }

        @Override
        public void onPageScrollStateChanged(int state) { }
    };

}
