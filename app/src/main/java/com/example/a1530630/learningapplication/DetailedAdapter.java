package com.example.a1530630.learningapplication;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.UserModuleProcess;


import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailedAdapter extends ArrayAdapter<UserModuleProcess>
{
    private static String detailTag = "DetailedAdapter";
    private Context detContext;
    int detResources;
    SQLiteManage db;
    int proId;
    int idUM;
    String uName;
    String pWord;
    String fName;
    String eMail;
    String compDate;
    int modUM;
    int lOne;
    int lTwo;
    int lThree;
    int lFour;
    int lFive;



    public DetailedAdapter(Context context, int resources, ArrayList<UserModuleProcess>objects)
    {
        super(context,resources,objects);
        detContext = context;
        detResources = resources;

    }

    @Nullable
    @Override
    public View getView(int position, View contextView, ViewGroup parent)
    {
        db = new SQLiteManage(detContext);
        int umProID = getItem(position).getUserProID();
        int umUserID = getItem(position).getUserID();
        String umUserN = getItem(position).getUserN();
        String umPass = getItem(position).getPassW();
        String umFullN = getItem(position).getFullN();
        String umEmail = getItem(position).getEmailAddress();
        String umDate = getItem(position).getDateCompleted();
        int umModuleID = getItem(position).getModuleID();
        int umLessOne = getItem(position).getLessonOne();
        int umLessTwo = getItem(position).getLessonTwo();
        int umLessThree = getItem(position).getLessonThree();
        int umLessFour = getItem(position).getLessonFour();
        int umLessFive = getItem(position).getLessonFive();

        proId = umProID;
        idUM = umUserID;
        uName = umUserN;
        pWord = umPass;
        fName = umFullN;
        eMail = umEmail;
        compDate = umDate;
        modUM = umModuleID;
        lOne = umLessOne;
        lTwo = umLessTwo;
        lThree = umLessThree;
        lFour = umLessFour;
        lFive = umLessFive;

        LayoutInflater inflater = LayoutInflater.from(detContext);
        contextView = inflater.inflate(detResources, parent, false);

        TextView umProIDTV = contextView.findViewById(R.id.userProIDText1);
        TextView umUserIDTV = contextView.findViewById(R.id.userIDText1);
        TextView umUserNTV = contextView.findViewById(R.id.userText1);
        TextView umPassWTV = contextView.findViewById(R.id.passText1);
        TextView umFullNTV = contextView.findViewById(R.id.fullText1);
        TextView umEmailTV = contextView.findViewById(R.id.emailText1);
        TextView umDateTV = contextView.findViewById(R.id.dateText1);
        TextView umModuleTV = contextView.findViewById(R.id.moduleText1);
        TextView umLess1TV = contextView.findViewById(R.id.lesson1Text);
        TextView umLess2TV = contextView.findViewById(R.id.lesson2Text);
        TextView umLess3TV = contextView.findViewById(R.id.lesson3Text);
        TextView umLess4TV = contextView.findViewById(R.id.lesson4Text);
        TextView umLess5TV = contextView.findViewById(R.id.lesson5Text);

        umProIDTV.setText(String.valueOf(proId));
        umUserIDTV.setText(String.valueOf(idUM));
        umUserNTV.setText(String.valueOf(uName));
        umPassWTV.setText(String.valueOf(pWord));
        umFullNTV.setText(String.valueOf(fName));
        umEmailTV.setText(String.valueOf(eMail));
        umDateTV.setText(String.valueOf(compDate));
        umModuleTV.setText(String.valueOf(modUM));
        umLess1TV.setText(String.valueOf(lOne));
        umLess2TV.setText(String.valueOf(lTwo));
        umLess3TV.setText(String.valueOf(lThree));
        umLess4TV.setText(String.valueOf(lFour));
        umLess5TV.setText(String.valueOf(lFive));


        return contextView;
    }
}
