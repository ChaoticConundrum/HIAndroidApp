package com.ethannjc.keyboardtest;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by ecox15 on 11/8/2016.
 */

public class MacroRecorder implements TextWatcher {

    private long startTime;
    private HashMap<Long, String> data;

    public MacroRecorder() {
        startTime = System.currentTimeMillis();
        data = new HashMap<Long, String>();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        data.put(System.currentTimeMillis(), charSequence.toString());
        Log.d("[MacroRecorder] " + data.size() + ": ", charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
