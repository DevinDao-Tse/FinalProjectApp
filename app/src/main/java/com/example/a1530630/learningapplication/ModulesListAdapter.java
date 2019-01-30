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
import com.example.a1530630.learningapplication.Models.Modules;
import java.util.ArrayList;

public class ModulesListAdapter extends ArrayAdapter<Modules> {
    private static String TAG = "ModulesListAdapter";
    private Context mcontext;
    int mresources;
    SQLiteManage db;
    int numcon;
    int idcon;

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
        db = new SQLiteManage(mcontext);
       int num = getItem(position).getModuleNum();
       numcon = num;

        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(mresources, parent, false);

        TextView modText = (TextView)convertView.findViewById(R.id.AdaptMod);
        modText.setText("Module "+ String.valueOf(num));

        Button editbtn = (Button)convertView.findViewById(R.id.EditBtn);
        Button deletebtn = (Button)convertView.findViewById(R.id.DeleteBtn);

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mcontext, Editing.class);
                mcontext.startActivity(i);
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(db.DeleteModule(numcon)) {
                    Intent i = new Intent(mcontext, Store.class);
                    mcontext.startActivity(i);
                }
            }
        });

        return convertView;
    }


}
