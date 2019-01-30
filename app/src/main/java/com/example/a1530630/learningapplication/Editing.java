package com.example.a1530630.learningapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.w3c.dom.Text;

public class Editing extends AppCompatActivity {

    Button pick1,pick2,save,add;
    TextView file1,file2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
        }

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
                        .withRequestCode(1000)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            String path = getApplicationContext().getFilesDir().getAbsolutePath();
            int count =0;

            file1.setText(filePath);
            file2.setText(filePath);
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
}
