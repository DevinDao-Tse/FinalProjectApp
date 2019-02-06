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
    public static final String AudandImg_MODULE="ModuleNumber";
    public static final String AudandImg_AUDIO_COLUMN="Audio";
    public static final String AudandImg_IMAGE_COLUMN="Image";
    public static final String AudandImg_LESSON_COLUMN="LessonNumber";

    public static String CREATE_AudandImg_TABLE = "CREATE TABLE "+AudandImg_TABLE_NAME+" ("
            + AudandImg_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AudandImg_MODULE+ " INTEGER, "
            + AudandImg_LESSON_COLUMN+" INTEGER, "
            + AudandImg_AUDIO_COLUMN+ " BLOB, "
            + AudandImg_IMAGE_COLUMN+ " BLOB); ";

            //+ AudandImg_LESSON_COLUMN+ "TEXT);";


    private Integer FileID,ModuleNum, LessonNum;
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
    public AudioAndImages(int m, byte[] img, int l)
    {
        this.FileID = null;
        this.ModuleNum = m;
        this.file = null;
        this.bitmap = img;
        this.LessonNum = l;
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

    public Integer getModuleNum() { return ModuleNum; }

    public void setModuleNum(Integer moduleNum) { ModuleNum = moduleNum; }

    public Integer getLessonNum() { return LessonNum; }

    public void setLessonNum(Integer lessonNum) { LessonNum = lessonNum; }
}
