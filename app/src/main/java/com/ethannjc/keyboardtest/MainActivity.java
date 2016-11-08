package com.ethannjc.keyboardtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText textField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textField = (EditText) findViewById(R.id.textField);
        textField.addTextChangedListener(new MacroRecorder());
        textField.requestFocus();
    }

}
