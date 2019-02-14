package com.example.a1530630.learningapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Editing extends AppCompatActivity {

    Button pick1,pick2,save,load;
    ImageView home;
    TextView file1,file2,less,modu;
    SQLiteManage db;
    int les,mod;
    String[] extensions ={"jpg","jpeg","png","tif"};

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
            Intent i = new Intent(Editing.this,Store_Lessons.class);
            i.putExtra("Module",mod);
            startActivity(i); }
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
            file1.setText(filePath);
            String filename = filePath.substring(filePath.lastIndexOf("/")+1);
            String path = getApplication().getCacheDir().getAbsolutePath();
            File dir = new File(path,"myDir");
            if(!dir.exists()){dir.mkdir();}

            try
            {
                audFile = new File(filePath);
                Log.i("audio",filePath.substring(filePath.lastIndexOf("/")+1));

                audFis = new FileInputStream(audFile);
                OutputStream outputStream = new FileOutputStream(audFile);
                byte[] arr = new byte[audFis.available()];
                audFis.read(arr);
                outputStream.write(arr);
                audFis.close();
                outputStream.close();
            }
            catch (Exception e){Log.e("Error", e.getMessage());}


        }
        if (requestCode == 2000 && resultCode == RESULT_OK)
        {
            file2.setText(filePath);
            String extend = filePath.substring(filePath.lastIndexOf(".")+1).toLowerCase();

            if(!Arrays.asList(extensions).contains(extend))
            {
                IncorrectFileType(findViewById(R.id.filetypes));
                Toast.makeText(this,extend,Toast.LENGTH_SHORT).show();
                Log.i("Extenstion",extend);
                save.setEnabled(false);
            }
            else { save.setEnabled(true); }

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

    @Override
    public void onBackPressed() { super.onBackPressed(); }


    // convert from bitmap to byte array
    public static byte[] getBytesImg(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        return stream.toByteArray();
    }

    public void IncorrectFileType(View view)
    {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Editing.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Wrong File type");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


}
