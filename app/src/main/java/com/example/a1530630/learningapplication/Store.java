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


        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
        }

        String path = FilePickerActivity.RESULT_FILE_PATH;

        add = (Button)findViewById(R.id.Testbtn);
        homebtn = (ImageView) findViewById(R.id.HomeButton);
        pick = (Button) findViewById(R.id.Pickbtn);


        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(Store.this)
                        .withRequestCode(1000)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });

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


    //get number of files in zip file
    int zipEntriesCount(String path) throws IOException {
        ZipFile zf= new ZipFile(path);
        return zf.size();
    }

    ZipFile zipFiles(String path) throws  IOException
    {
        ZipFile zf= new ZipFile(path);
        return zf;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            String path = getApplicationContext().getFilesDir().getAbsolutePath();
            int count =0;

            try
            {
               count = zipEntriesCount(filePath);
            }
            catch (Exception e) { Log.e("ERROR", e.toString()); }


            //Bitmap bmImg = BitmapFactory.decodeFile(filePath);
            //img.setImageBitmap(bmImg);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case 1001:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                { Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
                }
                else
                    {
                        Toast.makeText(this,"Permission not granted",Toast.LENGTH_LONG).show();
                        finish();
                    }
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
