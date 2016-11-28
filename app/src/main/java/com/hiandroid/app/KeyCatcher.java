package com.hiandroid.app;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.HashMap;

public class KeyCatcher implements TextWatcher {

    // Use TextWatcher on the hidden EditText widget to capture ascii

    private EditText textField;
    private long startTime;
    private HashMap<Long, Integer> data;

    public KeyboardWriter keyboardWriter = null;

    public KeyCatcher(EditText subject) {
        textField = subject;
        startTime = System.currentTimeMillis();
        data = new HashMap<Long, Integer>();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() > 0) {
            int ascii = charSequence.charAt(charSequence.length()-1);
            Log.d("[KeyCatcher]", "ASCII: " + Integer.toString(ascii));
            data.put(System.currentTimeMillis(), ascii);
            keyboardWriter.setAsciiKey(ascii, true);
            keyboardWriter.setAsciiKey(ascii, false);
        } else {
            // Handle Backspaces
            Log.d("[KeyCatcher]", "Backspace");
            data.put(System.currentTimeMillis(), 8);
            keyboardWriter.setKey(ScanCoder.Key.K_DEL, true);
            keyboardWriter.setKey(ScanCoder.Key.K_DEL, false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // Always keep the EditText's content set to "~" as a hacky solution to not catching all backspaces
        // Has to remove and add itself to the textField to prevent calling itself recursively
        textField.removeTextChangedListener(this);
            editable.clear();
            editable.append("~");
        textField.addTextChangedListener(this);
    }
}
