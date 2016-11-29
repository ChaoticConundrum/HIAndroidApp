package com.hiandroid.app;

import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;

public class KeyboardWriter {

    private HashSet<ScanCoder.Key> pressed = null;
    private ScanCoder scanCoder = null;

    public KeyboardWriter(){
        pressed = new HashSet<ScanCoder.Key>();
        scanCoder = new ScanCoder();
    }

    void setKey(ScanCoder.Key code, boolean press){
        if(scanCoder == null)
            return;
        if(press) {
            pressed.add(code);
        } else {
            pressed.remove(code);
        }
        //Log.d("[KeyboardWriter]", "Pressed: " + pressed.size());
        scanCoder.sendCodes(pressed.toArray(new ScanCoder.Key[0]));
    }

    void setAsciiKey(int ascii, boolean press){
        if(scanCoder == null)
            return;
        if(press) {
            pressed.addAll(Arrays.asList(ScanCoder.Key.asciiScanCode(ascii)));
        } else {
            pressed.removeAll(Arrays.asList(ScanCoder.Key.asciiScanCode(ascii)));
        }
        //Log.d("[KeyboardWriter]", "Pressed: " + pressed.size());
        scanCoder.sendCodes(pressed.toArray(new ScanCoder.Key[0]));
    }

    void startRecordMacro(int number){

    }
    void stopRecordMacro(int number){

    }

    void playRecordMacro(int number){

    }
}
