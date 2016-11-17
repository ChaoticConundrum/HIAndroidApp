package com.hiandroid.app;

public class KeyboardWriter {

    private ScanCoder scanCoder = null;

    public KeyboardWriter(){
        scanCoder = new ScanCoder();
    }

    void setKey(int code, boolean press){
        byte bytes[] = new byte[8];
        if(press) {
            scanCoder.sendCodes(ScanCoder.asciiToScan(code));
        }

    }

    void startRecordMacro(int number){

    }
    void stopRecordMacro(int number){

    }

    void playRecordMacro(int number){

    }
}
