package com.example.a1530630.learningapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.AudioAndImages;
import com.example.a1530630.learningapplication.Models.Modules;

import java.util.ArrayList;

public class LessonsViewAdapter extends ArrayAdapter<AudioAndImages> {
    private Context mcontext;
    int mresources;
    SQLiteManage db;

    public LessonsViewAdapter(Context context, int resources, ArrayList<AudioAndImages> objects)
    {
        super(context,resources,objects);
        mcontext = context;
        mresources = resources;
    }
    @Nullable
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        db = new SQLiteManage(mcontext);

        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(mresources, parent, false);


        return convertView;
    }


}
