package com.example.a1530630.learningapplication;

import android.content.Intent;
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
    private Button next,back;
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

        tutorialAdapter = new TutorialAdapter(this);
        viewPager.setAdapter(tutorialAdapter);

        addDots(0);
        viewPager.addOnPageChangeListener(viewListener);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(next.getText().toString().equals("FINISH"))
                {
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

    }

    public void addDots(int position)
    {
        dots = new TextView[3];
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

            if(position ==0)
            {
                next.setEnabled(true);
                back.setEnabled(false);
                back.setVisibility(View.INVISIBLE);

                next.setText("NEXT");
                back.setText("");
            }
            else if(position == dots.length-1)
            {
                next.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);

                next.setText("FINISH");
                back.setText("BACK");
            }
            else
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
