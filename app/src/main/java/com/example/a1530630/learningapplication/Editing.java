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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Editing extends AppCompatActivity {

    Button pick1,pick2,save,load;
    ImageView home;
    TextView file1,file2,less,modu;
    SQLiteManage db;
    int les,mod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);

        db = new SQLiteManage(this);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
        }

        Intent i = getIntent();

        mod = i.getIntExtra("Module",0);
//        les = Integer.parseInt(i.getStringExtra("Lesson"));
        les = i.getIntExtra("Lesson",0);

        home = (ImageView)findViewById(R.id.HomeButton);

        less = (TextView)findViewById(R.id.textView);
        less.setText(String.valueOf(les)+ " / " +String.valueOf(mod));



        pick1 = (Button) findViewById(R.id.Pickbtn);
        pick2 = (Button) findViewById(R.id.Pick2btn);
        save = (Button) findViewById(R.id.SaveBtn);

        file1 = (TextView)findViewById(R.id.FileText);
        file2 = (TextView)findViewById(R.id.File2Text);


        pick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { new MaterialFilePicker().withActivity(Editing.this).withRequestCode(1000).withHiddenFiles(true).start(); }
        });
        pick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { new MaterialFilePicker().withActivity(Editing.this).withRequestCode(2000).withHiddenFiles(true).start(); }
        });

        homeButton();
    }

    private void homeButton(){home.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Editing.this,Store_Lessons.class);startActivity(i); }
    });}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final AudioAndImages allFiles = new AudioAndImages();
        String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
        File imgFile; //get file from download path
        File audFile; //get file from download path
        FileInputStream imgFis =null; //read file for img
        FileInputStream audFis = null; //read file for audio
        ByteArrayOutputStream stream = new ByteArrayOutputStream();//to byte for img
        ByteArrayOutputStream bos = new ByteArrayOutputStream(); //to byte for audio

        if (requestCode == 1000 && resultCode == RESULT_OK)
        {
            byte[] byteAud= new byte[30000];
            audFile = new File(filePath);
            try
            {
                audFis = new FileInputStream(audFile);
                for(int read; (read = audFis.read(byteAud)) != -1;)
                {
                    bos.write(byteAud,0,read);
                    file1.setText(String.valueOf(read));
                }
                bos.flush();
                bos.close();
                // byteAud = new byte[(int)audFile.length()];
            }
            catch (Exception e){Log.e("Error", e.getMessage());}
            byte[] set = bos.toByteArray();
            allFiles.setByteAud(set);
        }
        if (requestCode == 2000 && resultCode == RESULT_OK)
        {
            file2.setText(filePath);
            imgFile = new File(filePath);
            try {
                imgFis = new FileInputStream(imgFile);
                Bitmap bmImg = BitmapFactory.decodeStream(imgFis);
                bmImg.compress(Bitmap.CompressFormat.JPEG,0,stream);
                byte[] byteImg = stream.toByteArray();
                allFiles.setByteImg(byteImg);
            }
            catch(Exception e){ Log.e("Error", e.getMessage()); }
        }

        allFiles.setModuleNum(mod);
        allFiles.setLessonNum(les);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.createRowAud(allFiles);
                Intent i = new Intent(Editing.this, Editing.class);
                i.putExtra("Module",mod);
                i.putExtra("Lesson",les);
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
