package com.example.a1530630.learningapplication;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.Module_Results;
import com.example.a1530630.learningapplication.Models.User;

public class DetailedAdapter extends CursorAdapter
{
    private static String detailTag = "DetailedAdapter";
    SQLiteManage db;

    public DetailedAdapter(Context context, Cursor cursor)
    {
        super(context,cursor,0);
    }

    @Nullable
    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        //Find fields to populate
        TextView tvUserID = view.findViewById(R.id.userIDText);
        TextView tvUser = view.findViewById(R.id.userText1);
        TextView tvPass = view.findViewById(R.id.passText1);
        TextView tvFull = view.findViewById(R.id.fullText1);
        TextView tvEmail = view.findViewById(R.id.emailText1);
        TextView tvDate = view.findViewById(R.id.dateText1);
        TextView tvModule = view.findViewById(R.id.moduleText1);
        TextView tvLesOne = view.findViewById(R.id.lesson1Text);
        TextView tvLesTwo = view.findViewById(R.id.lesson2Text);
        TextView tvLesThree = view.findViewById(R.id.lesson3Text);
        TextView tvLesFour = view.findViewById(R.id.lesson4Text);
        TextView tvLesFive = view.findViewById(R.id.lesson5Text);

        //Extract properties from cursor
        int userID = cursor.getInt(cursor.getColumnIndex(User.COLUMN_ID));
        String userName1 = cursor.getString(cursor.getColumnIndex(User.COLUMN_USERNAME));
        String passWord = cursor.getString(cursor.getColumnIndex(User.COLUMN_PASSWORD));
        String fullName1 = cursor.getString(cursor.getColumnIndex(User.COLUMN_FULL_NAME));
        String emailAdd = cursor.getString(cursor.getColumnIndex(User.COLUMN_EMAIL));
        String dateComp = cursor.getString(cursor.getColumnIndex(User.COLUMN_CREATED));
        int moduleId = cursor.getInt(cursor.getColumnIndex(Module_Results.MODULE_RESULT_COLUMN_MODULE_ID));
        int lessonOne = cursor.getInt(cursor.getColumnIndex(Module_Results.MODULE_RESULT_COLUMN_LESSON_ONE));
        int lessonTwo = cursor.getInt(cursor.getColumnIndex(Module_Results.MODULE_RESULT_COLUMN_LESSON_TWO));
        int lessonThree = cursor.getInt(cursor.getColumnIndex(Module_Results.MODULE_RESULT_COLUMN_LESSON_THREE));
        int lessonFour = cursor.getInt(cursor.getColumnIndex(Module_Results.MODULE_RESULT_COLUMN_LESSON_FOUR));
        int lessonFive = cursor.getInt(cursor.getColumnIndex(Module_Results.MODULE_RESULT_COLUMN_LESSON_FIVE));

        //Set the textview fields with extracted cursor properties
        tvUserID.setText(String.valueOf(userID));
        tvUser.setText(String.valueOf(userName1));
        tvPass.setText(String.valueOf(passWord));
        tvFull.setText(String.valueOf(fullName1));
        tvEmail.setText(String.valueOf(emailAdd));
        tvDate.setText(String.valueOf(dateComp));
        tvModule.setText(String.valueOf(moduleId));
        tvLesOne.setText(String.valueOf(lessonOne));
        tvLesTwo.setText(String.valueOf(lessonTwo));
        tvLesThree.setText(String.valueOf(lessonThree));
        tvLesFour.setText(String.valueOf(lessonFour));
        tvLesFive.setText(String.valueOf(lessonFive));

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        db = new SQLiteManage(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View retView = inflater.inflate(R.layout.activity_detailed_adapter, parent, false);

        return retView;
    }


}
