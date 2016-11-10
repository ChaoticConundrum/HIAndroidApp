package com.hiandroid.app;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

public class CustomEditText extends EditText {

    // Custom EditText widget with a custom InputConnectionWrapper to catch key events
    // Does not work because soft keyboards do not generate key events

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CustomEditText(Context context) {
        super(context);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo info) {
        Log.d("[ayyyy] ", "created CustomInputConnection");
        return new CustomInputConnection(super.onCreateInputConnection(info), true);
    }

    private class CustomInputConnection extends InputConnectionWrapper {
        public CustomInputConnection(InputConnection original, boolean mutable) {
            super(original, mutable);
        }

        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {

            Log.d("[CustomEditText] ", "deleteSurrondingText(" + beforeLength +", " + afterLength + ") called");

            return super.deleteSurroundingText(beforeLength, afterLength);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            Log.d("[CustomEditText] ", Integer.toString(event.getKeyCode()) + " was pressed");
            return super.sendKeyEvent(event);
        }
    }
}
