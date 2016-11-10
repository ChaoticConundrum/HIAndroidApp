package com.hiandroid.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

// List of dead ends:
// 1) using TextWatcher to capture keys - does not reliably catch backspace or return
// 2) View.OnKeyListener - "This is only useful for hardware keyboards; a software input method has no obligation to trigger this listener."
// 3) Custom EditText and InputConnection interception - same problem as #2

public class MainActivity extends AppCompatActivity {

    private CustomEditText customField;
    private EditText textField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customField = (CustomEditText) findViewById(R.id.customField);
        textField = (EditText) findViewById(R.id.textField);
        textField.requestFocus();
        textField.addTextChangedListener(new KeyCatcher(textField));
    }

}
