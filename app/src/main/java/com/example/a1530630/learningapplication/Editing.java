package com.example.a1530630.learningapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Editing extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final int REQ_PERMISSION = 120;
    public DrawerLayout dl;
    public ActionBarDrawerToggle t;
    Button pick1,pick2,save,load;
    ImageView home,imagepreview;
    TextView file1,file2,less,modu;
    SQLiteManage db;
    int les,mod;
    String[] extensions ={"jpg","jpeg","png","tif"};
    Context context;
    NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);
        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        reqPermission();

        db = new SQLiteManage(this);
        Intent i = getIntent();

        mod = i.getIntExtra("Module",0);
//        les = Integer.parseInt(i.getStringExtra("Lesson"));
        les = i.getIntExtra("Lesson",0);

        home = (ImageView)findViewById(R.id.HomeButton);

        less = (TextView)findViewById(R.id.textView);
        less.setText("Module "+String.valueOf(mod)+"\nLesson "+String.valueOf(les));

        pick1 = (Button) findViewById(R.id.Pickbtn);
        pick2 = (Button) findViewById(R.id.Pick2btn);
        save = (Button) findViewById(R.id.SaveBtn);

        imagepreview = (ImageView)findViewById(R.id.imageViewSelect);

        //file1 = (TextView)findViewById(R.id.FileText);
        file2 = (TextView)findViewById(R.id.File2Text);

        pick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { new MaterialFilePicker().withActivity(Editing.this).withRequestCode(1000).withHiddenFiles(true).start(); }
        });
        pick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { new MaterialFilePicker().withActivity(Editing.this).withRequestCode(2000).withHiddenFiles(true).start(); }
        });

        t = new ActionBarDrawerToggle(this, dl,R.string.nav_open, R.string.nav_close);
        dl.addDrawerListener(t);
        t.syncState(); //getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dl.addDrawerListener(new DrawerLayout.DrawerListener()
        {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) { }
            @Override
            public void onDrawerOpened(@NonNull View view)
            {
                SharedPreferences settings = getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);
                String userName = settings.getString("Username",null);
                TextView user = findViewById(R.id.nav_header_textView);
                user.setText(userName);
            }
            @Override
            public void onDrawerClosed(@NonNull View view) {}
            @Override
            public void onDrawerStateChanged(int i) {}
        });

        nv = findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(this);


        homeButton();
    }




   public void reqPermission()
   {
       int reqEx = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
       if(reqEx != PackageManager.PERMISSION_GRANTED)
       {
           ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQ_PERMISSION);
       }
   }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "granted",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "not",Toast.LENGTH_SHORT).show();
        }
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
            //file1.setText(filePath);
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
            else {
                save.setEnabled(true);
            }

            imgFile = new File(filePath);
            try {
                imgFis = new FileInputStream(imgFile);
                Bitmap bmImg = BitmapFactory.decodeStream(imgFis);
                bmImg.compress(Bitmap.CompressFormat.JPEG,0,stream);
                byte[] byteImg = stream.toByteArray();
                allFiles.setByteImg(byteImg);
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteImg,0,byteImg.length);
                imagepreview.setImageBitmap(bitmap);


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
    public void onBackPressed() { super.onBackPressed(); }


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

    public boolean onOptionsItemSelected(MenuItem item) { if(t.onOptionsItemSelected(item)) return true;return super.onOptionsItemSelected(item); }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent i;
        switch (menuItem.getItemId())
        {
            case R.id.nav_menu:
            {
                i = new Intent(this,Main_Menu.class);
                startActivity(i);
                return true;
            }
            case R.id.nav_profile:
            {
                i = new Intent(this,Profile.class);
                startActivity(i);
                return true;
            }

            case R.id.nav_summary:
            {
                i = new Intent(this,SummaryReport.class);
                startActivity(i);
                return true;
            }

            case R.id.nav_exit:
            {
                SharedPreferences settings = getSharedPreferences(Login.MyPreferences, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.commit();
                i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
                return true;
            }
            case R.id.nav_add: {
                i = new Intent(getApplicationContext(), Store.class);
                startActivity(i);
                return true;
            }
            case R.id.nav_tutorial: {
                i = new Intent(getApplicationContext(), Tutorial.class);
                startActivity(i);
                return true;
            }
        }
        return true;
    }

}
