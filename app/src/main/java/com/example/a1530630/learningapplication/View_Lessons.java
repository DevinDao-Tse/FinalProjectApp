package com.example.a1530630.learningapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.a1530630.learningapplication.Database.SQLiteManage;
import com.example.a1530630.learningapplication.Models.AudioAndImages;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class View_Lessons extends AppCompatActivity {

    TextView module,lesson;
    int mod,les;
    SQLiteManage db;
    TableRow header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__lessons);

        db = new SQLiteManage(this);

        module = (TextView)findViewById(R.id.textView3);
        lesson = (TextView)findViewById(R.id.textView4);

        Intent i = getIntent();
        mod = i.getIntExtra("Module",0);
        les = i.getIntExtra("Lesson",0);

        module.setText("Module "+ mod);
        lesson.setText("Lesson "+les);

        ListView listView = (ListView)findViewById(R.id.Images);
        ArrayList<AudioAndImages> list = new ArrayList<>();

        Cursor cursor = db.getImgsInfo(mod,les);
        if(cursor.moveToFirst())
        {
            do
                {
                    AudioAndImages img = new AudioAndImages();
                    img.setByteImg(cursor.getBlob(cursor.getColumnIndex(AudioAndImages.AudandImg_IMAGE_COLUMN)));
                    list.add(img);
                }
                while (cursor.moveToNext());
        }

        LessonsViewAdapter lessonsViewAdapter = new LessonsViewAdapter(this, R.layout.lessons_view_adapter, list);
        listView.setAdapter(lessonsViewAdapter);

    }
}
