package com.example.a1530630.learningapplication;

import android.util.Log;

import java.io.IOException;
import java.security.MessageDigest;

public class MD5
{
    public String hashPass(String pass) throws IOException
    {
        String hashedPass= null;

        try
        {
            MessageDigest md =  MessageDigest.getInstance("MD5"); //instance for MD5
            md.update(pass.getBytes()); //get byte of password
            byte[] bytes = md.digest(); //variable byte
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<bytes.length;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashedPass = sb.toString();
        }catch (Exception e){
            Log.e("Error", e.getMessage());
        }
        return hashedPass;
    }
}
