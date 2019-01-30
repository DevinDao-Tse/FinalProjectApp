package com.example.a1530630.learningapplication.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class AudioAndImages
{
    public static final String AudandImg_TABLE_NAME="FILES";
    public static final String AudandImg_ID="FilesID";
    public static final String AudandImg_WORD="Word";
    public static final String AudandImg_AUDIO_COLUMN="Audio";
    public static final String AudandImg_IMAGE_COLUMN="Image";

    public static String CREATE_AudandImg_TABLE = "CREATE TABLE "+AudandImg_TABLE_NAME+" ("
            + AudandImg_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AudandImg_WORD+ " TEXT, "
            + AudandImg_AUDIO_COLUMN+ " BLOB, "
            + AudandImg_IMAGE_COLUMN+ " BLOB);";

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    private Integer FileID;
    private String word;
    private Bitmap bitmap;
    private File file;

    public AudioAndImages(){}
    public AudioAndImages(String w,Bitmap img, File aud)
    {
        this.FileID = null;
        this.word = w;
        this.bitmap = img;
        this.file= aud;
    }


    public Integer getFileID() {
        return FileID;
    }

    public void setFileID(Integer fileID) {
        FileID = fileID;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getWord() { return word; }

    public void setWord(String word) { this.word = word; }

    public File getFile() { return file; }

    public void setFile(File file) { this.file = file; }
}
