package com.example.a1530630.learningapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Store extends AppCompatActivity {

    Button pick,save;
    TextView filetxt,numFile;
    ImageView img,homebtn;
    String filePath;

    ZipOutputStream zOut = null;

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
        save = (Button)findViewById(R.id.SaveBtn);

        String path = FilePickerActivity.RESULT_FILE_PATH;

        filetxt = (TextView)findViewById(R.id.FileText);
        numFile = (TextView)findViewById(R.id.ZipFile);
        filetxt.setText(path);


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

            try {
                count = zipEntriesCount(filePath);
                String FILENAME = "hello_file";
                String FOLDERNAME = "sub";
                String string = "hello world!";

                Context context = getApplicationContext();
                String folder = context.getFilesDir().getAbsolutePath() + File.separator + FOLDERNAME;

                File subFolder = new File(folder);

                if (!subFolder.exists()) {
                    subFolder.mkdirs();
                }

                FileOutputStream outputStream = new FileOutputStream(new File(subFolder, filePath));

                //outputStream.write(string.getBytes());
                outputStream.close();

            } catch (FileNotFoundException e) {
                Log.e("ERROR", e.toString());
            } catch (IOException e) {
                Log.e("ERROR", e.toString());
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

    private void homeButton(){homebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Store.this,Main_Menu.class);
            startActivity(i);
        }
    });}
}
