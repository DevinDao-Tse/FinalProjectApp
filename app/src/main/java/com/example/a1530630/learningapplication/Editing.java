package com.example.a1530630.learningapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.AudioAndImages;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Editing extends AppCompatActivity {

    Button pick1,pick2,save;
    ImageView home;
    TextView file1,file2;
    SQLiteManage db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);

        db = new SQLiteManage(this);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
        }

        home = (ImageView)findViewById(R.id.HomeButton);

        pick1 = (Button) findViewById(R.id.Pickbtn);
        pick2 = (Button) findViewById(R.id.Pick2btn);
        save = (Button) findViewById(R.id.SaveBtn);

        file1 = (TextView)findViewById(R.id.FileText);
        file2 = (TextView)findViewById(R.id.File2Text);

        pick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(Editing.this)
                        .withRequestCode(1000)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });
        pick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialFilePicker()
                        .withActivity(Editing.this)
                        .withRequestCode(2000)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });
        homeButton();
    }

    private void homeButton(){home.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Editing.this,Main_Menu.class);
            startActivity(i);
        }
    });}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
        File imgFile; File audFile;
        FileInputStream imgFis =null; FileInputStream audFis = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        byte[] byteAud = new byte[8192];
        if (requestCode == 1000 && resultCode == RESULT_OK)
        {
            file1.setText(filePath);
            audFile = new File(filePath);
            try
            {
                audFis = new FileInputStream(audFile);
                byteAud = new byte[(int)audFile.length()];
            }
            catch (Exception e){Log.e("Error", e.getMessage());}
        }
        if (requestCode == 2000 && resultCode == RESULT_OK)
        {
            file2.setText(filePath);
            imgFile = new File(filePath);

            try
            {
                imgFis = new FileInputStream(imgFile);

                Bitmap bmImg = BitmapFactory.decodeStream(imgFis);
                bmImg.compress(Bitmap.CompressFormat.JPEG,0,stream);

            }
            catch(Exception e){ Log.e("Error", e.getMessage()); }

        }
       /* ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bmImg = BitmapFactory.decodeStream(imgFis);
        bmImg.compress(Bitmap.CompressFormat.JPEG,0,stream);*/
        byte[] byteImg = stream.toByteArray();

        final AudioAndImages allFiles = new AudioAndImages();
        allFiles.setByteAud(byteAud);
        allFiles.setByteImg(byteImg);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.createRowAud(allFiles);
                Intent i = new Intent(Editing.this, Main_Menu.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case 1001:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                { Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show(); }
                else
                { Toast.makeText(this,"Permission not granted",Toast.LENGTH_LONG).show();finish(); }
        }
    }


    // convert from bitmap to byte array
    public static byte[] getBytesImg(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


}
