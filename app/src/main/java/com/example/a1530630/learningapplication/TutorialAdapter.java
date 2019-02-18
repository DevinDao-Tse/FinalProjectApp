package com.example.a1530630.learningapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TutorialAdapter extends PagerAdapter
{
    Context context;
    LayoutInflater layoutInflater;

    public TutorialAdapter(Context context)
    {
        this.context = context;
    }

    public int[] sliderImages = {R.drawable.cap2n3, R.drawable.cap1,R.drawable.ic_settings,R.drawable.cap4};

    public String[] sliderHeader = {
            "Swipe screen to open side bar menu",
            "View your overall progression",
            "Click on module and choose lesson",
            "Check off Correct or Incorrect box to continue. Click play button to play audio."};



    @Override
    public int getCount() {
        return sliderHeader.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.sliders,container,false);

        ImageView sliderImageView = (ImageView)view.findViewById(R.id.sliderImage);
        TextView sliderTextHeader = (TextView)view.findViewById(R.id.sliderHeader);

        sliderImageView.setImageResource(sliderImages[position]);
        sliderTextHeader.setText(sliderHeader[position]);


        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
