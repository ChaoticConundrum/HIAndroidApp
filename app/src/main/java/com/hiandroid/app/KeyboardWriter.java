package com.hiandroid.app;

public class KeyboardWriter {

    private ScanCoder scanCoder = null;

    public KeyboardWriter(){
        scanCoder = new ScanCoder();
    }

    void setKey(int code, boolean press){
        byte bytes[] = new byte[8];
        if(press) {
            byte codes[] = ScanCoder.asciiToScan(code);
            // Modifiers
            bytes[0] |= codes[0];
            // Key
            bytes[2] = codes[1];
        }
        scanCoder.sendCodes(bytes);
    }

    void startRecordMacro(int number){

    }
    void stopRecordMacro(int number){

    }

    void playRecordMacro(int number){

    }
}
