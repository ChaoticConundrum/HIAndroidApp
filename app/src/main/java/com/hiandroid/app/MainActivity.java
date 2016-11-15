package com.hiandroid.app;

import android.content.pm.PackageManager;
import android.inputmethodservice.Keyboard;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

// List of dead ends:
// 1) using TextWatcher to capture keys - does not reliably catch backspace or return
// 2) View.OnKeyListener - "This is only useful for hardware keyboards; a software input method has no obligation to trigger this listener."
// 3) Custom EditText and InputConnection interception - same problem as #2

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST = 1;
    private CustomEditText customField;
    private EditText textField;

    private ScanCoder scanCoder = null;
    private KeyCatcher keyCatcher = null;

    public MainActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customField = (CustomEditText) findViewById(R.id.customField);
        textField = (EditText) findViewById(R.id.textField);
        textField.requestFocus();
        keyCatcher = new KeyCatcher(textField);
        textField.addTextChangedListener(keyCatcher);

        // Check for permission
        String perm = "android.permission.WRITE_EXTERNAL_STORAGE";
        int check = ContextCompat.checkSelfPermission(this, perm);
        if (check == PackageManager.PERMISSION_GRANTED) {
            // Have permission
            scanCoder = new ScanCoder();
            keyCatcher.scanCoder = scanCoder;
        } else {
            // Request permission
            ActivityCompat.requestPermissions(this, new String[]{ perm }, PERMISSION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST:
                // Check for success
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    scanCoder = new ScanCoder();
                    keyCatcher.scanCoder = scanCoder;
                }
        }
    }
}
