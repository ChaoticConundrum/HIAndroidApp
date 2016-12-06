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

        if (recording && recordingMacro != null) {
            // Trick to save macro base time on first keypress
            if(recordingTime == 0) {
                recordingTime = System.currentTimeMillis();
                recordingMacro.times.add(0L);
            } else {
                recordingMacro.times.add(System.currentTimeMillis() - recordingTime);
            }
            recordingMacro.keys.add(code.code());
            recordingMacro.states.add(press);
        }

        updateScanCoder();
    }

    void setMacroKey(Byte code, boolean press){
        if(scanCoder == null)
            return;

        ScanCoder.Key key = ScanCoder.Key.codeKey(code);

        if(press) {
            pressed.add(key);
        } else {
            pressed.remove(key);
        }

        ScanCoder.Key[] keys = pressed.toArray(new ScanCoder.Key[0]);
        scanCoder.sendCodes(keys);
    }

    void setAsciiKey(int ascii, boolean press){
        if(scanCoder == null)
            return;

        if(press) {
            pressed.addAll(Arrays.asList(ScanCoder.Key.asciiScanCode(ascii)));
        } else {
            pressed.removeAll(Arrays.asList(ScanCoder.Key.asciiScanCode(ascii)));
        }

        updateScanCoder();
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
}
