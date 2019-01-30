package com.example.a1530630.learningapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.Modules;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Store extends AppCompatActivity {

    Button pick,save,add;
    ImageView img,homebtn;
    String filePath;
    SQLiteManage db;
    //LinearLayout lay;

    ZipOutputStream zOut = null;

    static final int REQUEST_PERMISSION_KEY = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        db = new SQLiteManage(this);

        add = (Button)findViewById(R.id.Testbtn);
        homebtn = (ImageView) findViewById(R.id.HomeButton);

        ListView listView = (ListView)findViewById(R.id.listView);

        ArrayList<Modules> list = new ArrayList<>();
        Cursor cursor = db.getModuleNum();
        if(cursor.moveToFirst())
        {
            do
            {
                Modules modules = new Modules();
                modules.setModuleNum(cursor.getInt(cursor.getColumnIndex(Modules.MODULE_COLUMN_NUMBER)));
                list.add(modules);
            }while(cursor.moveToNext());
        }

        ModulesListAdapter adapter = new ModulesListAdapter(this, R.layout.module_adapter,list);
        listView.setAdapter(adapter);
        homeButton();
    }

    public void addnewFromDB(View v)
    {
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Cursor cursor = db.createNewModule();
        if(cursor.moveToFirst())
        {
            int NewNum = cursor.getInt(cursor.getColumnIndex(Modules.MODULE_COLUMN_NUMBER));
            NewNum =NewNum+1;
            Modules newOne = new Modules(NewNum);
            db.createModules(newOne);
            Toast.makeText(getApplicationContext(),"Module "+NewNum+" created",Toast.LENGTH_LONG).show();
        }
        else
        {
            int NewNum = 1;
            Modules newOne = new Modules(NewNum);
            db.createModules(newOne);
            Toast.makeText(getApplicationContext(),"Module 1 created",Toast.LENGTH_LONG).show();
        }
    }


    private void homeButton(){homebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Store.this,Main_Menu.class);
            startActivity(i);
        }
    });}
}
