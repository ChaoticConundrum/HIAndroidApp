package com.hiandroid.app;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class ScanCoder {

    private File hidFile;
    private FileWriter writer;
    private DataOutputStream hidDataStream = null;

    public ScanCoder(){
        try {
            hidDataStream = new DataOutputStream(new FileOutputStream("/dev/hidg0"));
        } catch(IOException e){
            Log.e("[ScanCoder]", "Failed to open char device for writing");
        }
    }

    public void sendCode(){
        if(hidDataStream == null)
            return;
        byte bytes[] = new byte[8];

        // Press keys
        bytes[2] = 0x04;
        try {
            hidDataStream.write(bytes);
        } catch(IOException e){
            Log.e("[ScanCoder]", "Failed to write char device");
        }

        // Unpress keys
        Arrays.fill(bytes, (byte)0);
        try {
            hidDataStream.write(bytes);
        } catch(IOException e){
            Log.e("[ScanCoder]", "Failed to write char device");
        }
    }
}
