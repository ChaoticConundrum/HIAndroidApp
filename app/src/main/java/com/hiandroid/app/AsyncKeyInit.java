package com.hiandroid.app;

import android.os.AsyncTask;

public class AsyncKeyInit extends AsyncTask<Void, Void, ScanCoder> {

    private KeyboardWriter keyboardWriter = null;

    public AsyncKeyInit(KeyboardWriter writer){
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
