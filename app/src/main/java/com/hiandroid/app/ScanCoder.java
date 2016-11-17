package com.hiandroid.app;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class ScanCoder {

    private Process process = null;
    private DataOutputStream cmdStream = null;

    public ScanCoder(){
        try {
            process = Runtime.getRuntime().exec("su");
            cmdStream = new DataOutputStream(process.getOutputStream());
        } catch(IOException e) {
            Log.e("[ScanCoder]", "Failed to open su shell");
        }
    }

    // Returns 2 bytes
    // The first byte contains modifier flags (shift only)
    // The second byte is the scan code for the symbol
    public static byte[] asciiToScan(int ascii){
        byte scan[] = new byte[2];
        if(ascii == 48){
            // Zero
            scan[0] = 0;
            scan[1] = 39;
        } else if(ascii >= 49 && ascii <= 57){
            // Numbers
            scan[0] = 0;
            scan[1] = (byte) (ascii - 49 + 30);
        } else if(ascii >= 65 && ascii <= 90) {
            // Uppercase alphas
            scan[0] = 0x02;
            scan[1] = (byte)(ascii - 65 + 4);
        } else if(ascii >= 97 && ascii <= 122) {
            // Lowercase alphas
            scan[0] = 0;
            scan[1] = (byte) (ascii - 97 + 4);
        } else {
            // Unknown
            Log.d("[ScanCoder]", "Unknown ASCII: " + ascii);
        }
        return scan;
    }

    public void sendCodes(byte[] codes){
        if(cmdStream == null || codes == null)
            return;
        try {
            // Make report
            String bystr = String.format(
                    "\\x%02x\\x%02x\\x%02x\\x%02x\\x%02x\\x%02x\\x%02x\\x%02x",
                    codes[0], 0, codes[2], codes[3], codes[4], codes[5], codes[6], codes[7]);
            //Log.d("[ScanCoder]", bystr);
            // Write report
            cmdStream.writeBytes("echo -n -e '" + bystr + "' > /dev/hidg0\n");
            cmdStream.flush();
        } catch(IOException e){
            Log.e("[ScanCoder]", "Failed to write char device " + e.toString());
        }
    }
}
