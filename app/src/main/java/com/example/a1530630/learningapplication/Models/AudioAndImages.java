package com.example.a1530630.learningapplication.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AudioAndImages
{
    public static final String AudandImg_TABLE_NAME="FILES";
    public static final String AudandImg_ID="FilesID";
    public static final String AudandImg_WORD="Word";
    public static final String AudandImg_AUDIO_COLUMN="Audio";
    public static final String AudandImg_IMAGE_COLUMN="Image";
    public static final String AudandImg_LESSON_COLUMN="Lesson";

    public static String CREATE_AudandImg_TABLE = "CREATE TABLE "+AudandImg_TABLE_NAME+" ("
            + AudandImg_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AudandImg_AUDIO_COLUMN+ " BLOB, "
            + AudandImg_IMAGE_COLUMN+ " BLOB); ";
            //+ AudandImg_LESSON_COLUMN+ "TEXT);";


    private Integer FileID;
    private String lesson;
    private byte[] bitmap,file;


    public AudioAndImages(){}
    public AudioAndImages(byte[] img, byte[] aud)
    {
        this.FileID = null;
        this.bitmap = img;
        this.file= aud;
    }
    public AudioAndImages( byte[] img)
    {
        this.FileID = null;
        this.bitmap = null;
        this.file = img;
    }


    public Integer getFileID() {
        return FileID;
    }

    public void setFileID(Integer fileID) {
        FileID = fileID;
    }

    public byte[] getByteImg() {
        return bitmap;
    }

    public void setByteImg(byte[] bitmap) {
        this.bitmap = bitmap;
    }

    public byte[] getByteAud() { return file; }

    public void setByteAud(byte[] file) { this.file = file; }

    public String getLesson() { return lesson; }

    public void setLesson(String lesson) { this.lesson = lesson; }
}
