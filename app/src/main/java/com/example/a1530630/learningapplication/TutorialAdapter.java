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

    public int[] sliderImages =
    {
        R.drawable.ic_settings, R.drawable.ic_document_box, R.drawable.ic_settings
    };

    public String[] sliderHeader =
    {
       "Page 1","Page 2","Page 3"
    };

    public String[] sliderText =
    {
        "This is text for page 1",
        "This is text for page 2",
        "This is text for page 3",
    };



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
        TextView sliderTextContent = (TextView)view.findViewById(R.id.sliderText);

        sliderImageView.setImageResource(sliderImages[position]);
        sliderTextHeader.setText(sliderHeader[position]);
        sliderTextContent.setText(sliderText[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}