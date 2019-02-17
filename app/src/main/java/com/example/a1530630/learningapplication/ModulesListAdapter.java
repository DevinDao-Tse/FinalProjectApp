package com.example.a1530630.learningapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.Modules;
import java.util.ArrayList;

import android.support.v7.widget.RecyclerView.*;
import android.widget.Toast;

public class ModulesListAdapter extends ArrayAdapter<Modules> {
    private static String TAG = "ModulesListAdapter";
    private Context mcontext;
    int mresources;
    SQLiteManage db;
    int numcon;
    TextView txt,modText;
    int lol;

    public ModulesListAdapter(Context context, int resources, ArrayList<Modules> objects)
    {
        super(context,resources,objects);
        mcontext = context;
        mresources = resources;
    }
    @Nullable
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowView = convertView;
        ViewHolder holder = null;

       db = new SQLiteManage(mcontext);
       int num = getItem(position).getModuleNum();
       numcon = num;
        String n = String.valueOf(num);

        lol = this.getItem(position).getModuleNum();

//        LayoutInflater inflater = LayoutInflater.from(mcontext);
//        convertView = inflater.inflate(mresources, parent, false);

        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.module_adapter, parent, false);


        rowView = (View)convertView.getTag();

        modText = (TextView)convertView.findViewById(R.id.AdaptMod);
        modText.setContentDescription(n);
        modText.setTextColor(ContextCompat.getColor(mcontext,R.color.colorWhite));
        modText.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
        modText.setText("Module "+ String.valueOf(num));

        final Button editbtn = (Button)convertView.findViewById(R.id.EditBtn);
        Button deletebtn = (Button)convertView.findViewById(R.id.DeleteBtn);

        editbtn.setTag(R.integer.btn_edit_view,position);


        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer n = (Integer) editbtn.getTag(R.integer.btn_edit_view);
                Intent i = new Intent(mcontext, Store_Lessons.class);
                n =n+1;
                i.putExtra("Module",n);
                Toast.makeText(mcontext, String.valueOf(n),Toast.LENGTH_SHORT).show();
                mcontext.startActivity(i);
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer n = (Integer) editbtn.getTag(R.integer.btn_edit_view);
                n =n+1;
                if(db.DeleteModule(n) && db.DeleteModImages(n)) {
                    Intent i = new Intent(mcontext, Store.class);
                    mcontext.startActivity(i);
                }
            }
        });

        convertView.setTag(convertView.getId(),position);
        return convertView;
    }


}
