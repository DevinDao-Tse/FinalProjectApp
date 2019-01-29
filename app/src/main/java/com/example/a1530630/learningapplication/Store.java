package com.example.a1530630.learningapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;

public class Store extends AppCompatActivity {

    Button pick;
    TextView filetxt,numFile;
    ImageView img,homebtn;
    static final int REQUEST_PERMISSION_KEY = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
        }


        pick = (Button)findViewById(R.id.Pickbtn);
        filetxt = (TextView)findViewById(R.id.FileText);
        numFile = (TextView)findViewById(R.id.ZipFile);
        img = (ImageView)findViewById(R.id.ImageTest);
        homebtn = (ImageView) findViewById(R.id.HomeButton);

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

    }
    private void homeButton(){homebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Store.this,Main_Menu.class);
            startActivity(i);
        }
    });}

    //get number of files in zip file
    int zipEntriesCount(String path) throws IOException {
        ZipFile zf= new ZipFile(path);
        return zf.size();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            // Do anything with file
            int count =0;

            try {
                count = zipEntriesCount(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            filetxt.setText(filePath);
            numFile.setText(String.valueOf(count));
            Bitmap bmImg = BitmapFactory.decodeFile(filePath);
            img.setImageBitmap(bmImg);
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
