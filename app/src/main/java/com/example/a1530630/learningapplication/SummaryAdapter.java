package com.example.a1530630.learningapplication;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.User;

import java.util.ArrayList;

public class SummaryAdapter extends ArrayAdapter<User>
{
    private static String SUMTAG = "SummaryAdapter";
    private Context sumContext;
    int sumResources;
    SQLiteManage db;
    String userNameCon;
    String fullNameCon;
    String emailCon;
    String dateCon;

    public SummaryAdapter(Context context, int resources, ArrayList<User> objects)
    {
        super(context, resources,objects);
        sumContext = context;
        sumResources = resources;
    }

    @Nullable
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        db = new SQLiteManage(sumContext);
        String name = getItem(position).getFullName();
        String user = getItem(position).getUsername();
        String email = getItem(position).getEmail();
        String dateSigned = getItem(position).getCreated();

        fullNameCon = name;
        userNameCon = user;
        emailCon = email;
        dateCon = dateSigned;

        LayoutInflater inflater = LayoutInflater.from(sumContext);
        convertView = inflater.inflate(sumResources, parent, false);

        TextView nameText = convertView.findViewById(R.id.nameID);
        TextView userText = convertView.findViewById(R.id.userNameID);
        TextView emailText = convertView.findViewById(R.id.emailID);
        TextView dateText = convertView.findViewById(R.id.dateID);

        nameText.setText(String.valueOf(fullNameCon));
        userText.setText(String.valueOf(userNameCon));
        emailText.setText(String.valueOf(emailCon));
        dateText.setText(String.valueOf(dateCon));


        return convertView;

    }

}
