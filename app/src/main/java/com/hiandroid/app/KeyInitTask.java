package com.hiandroid.app;

import android.os.AsyncTask;

public class KeyInitTask extends AsyncTask<Void, Void, ScanCoder> {

    private KeyboardWriter keyboardWriter = null;

    public KeyInitTask(KeyboardWriter writer){
        this.keyboardWriter = writer;
    }

    @Override
    protected ScanCoder doInBackground(Void... voids) {
        return new ScanCoder();
    }

    @Override
    protected void onPostExecute(ScanCoder scanCoder) {
        super.onPostExecute(scanCoder);
        keyboardWriter.setScanCoder(scanCoder);
    }
}
