package com.hiandroid.app;

import java.util.Arrays;
import java.util.HashSet;

public class KeyboardWriter {

    private HashSet<ScanCoder.Key> pressed = null;
    private ScanCoder scanCoder = null;

    private boolean recording;
    private long recordingTime;
    public Macro recordingMacro;

    public KeyboardWriter(){
        pressed = new HashSet<>();
        new KeyInitTask(this).execute();
    }

    public void setScanCoder(ScanCoder coder){
        scanCoder = coder;
    }

    private void updateScanCoder(){
        //Log.d("[KeyboardWriter]", "Pressed: " + pressed.size());
        ScanCoder.Key[] keys = pressed.toArray(new ScanCoder.Key[0]);
        //scanCoder.sendCodes(keys);
        new KeySendTask(scanCoder).execute(keys);
    }

    void setKey(ScanCoder.Key code, boolean press){
        if(scanCoder == null)
            return;

        if(press) {
            pressed.add(code);
        } else {
            pressed.remove(code);
        }

        // Add key to a recording macro
        saveMacroKey(code.code(), press);

        updateScanCoder();
    }

    void setMacroKey(Integer code, boolean press){
        if(scanCoder == null)
            return;

        ScanCoder.Key key = ScanCoder.Key.codeKey(code);

        if(press) {
            pressed.add(key);
        } else {
            pressed.remove(key);
        }

        // Add key to a recording macro
        saveMacroKey(code, press);

        ScanCoder.Key[] keys = pressed.toArray(new ScanCoder.Key[0]);
        scanCoder.sendCodes(keys);
    }

    void startRecordMacro(int number){
        //Log.d("[KeyboardWriter]", "Recording started");
        recording = true;
        recordingTime = 0;
        recordingMacro = new Macro("Macro " + number);
    }

    void stopRecordMacro(){
        //Log.d("[KeyboardWriter]", "Recording ended");
        recording = false;
    }

    void saveMacroKey(int code, boolean press){
        if (recording && recordingMacro != null) {
            // Trick to save macro base time on first keypress
            if(recordingTime == 0) {
                recordingTime = System.currentTimeMillis();
                recordingMacro.times.add(0L);
            } else {
                recordingMacro.times.add(System.currentTimeMillis() - recordingTime);
            }
            recordingMacro.keys.add(code);
            recordingMacro.states.add(press);
        }
    }
}
