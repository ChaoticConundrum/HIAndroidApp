package com.hiandroid.app;

import android.os.AsyncTask;

import java.util.HashSet;

public class AsyncKeySend extends AsyncTask<ScanCoder.Key[], Void, Void> {

    private ScanCoder scanCoder = null;

    public AsyncKeySend(ScanCoder coder){
        this.scanCoder = coder;
    }

    @Override
    protected Void doInBackground(ScanCoder.Key[]... keys) {
        scanCoder.sendCodes(keys[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
