package com.hiandroid.app;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

public class HardKeyboardActivity extends Activity {

    private GridLayout keyboardGrid;
    private HardKey[] keys;

    public HardKeyboardActivity() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        generateKeys();

        keyboardGrid = (GridLayout) findViewById(R.id.keyboardGrid);
        keyboardGrid.setColumnCount(16);
        keyboardGrid.setRowCount(6);
        for (HardKey key : keys) {
            key.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        // Key pressed down

                        return true;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        // Key released

                        return true;
                    }
                    return false;
                }
            });
            key.setText(key.name1);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, key.width);
            params.width = GridLayout.LayoutParams.MATCH_PARENT;
            params.height = GridLayout.LayoutParams.MATCH_PARENT;
            key.setLayoutParams(params);
            keyboardGrid.addView(key);
        }

        setContentView(R.layout.activity_main);

    }

    private void generateKeys() {
        HardKey[] init = {
                // 0 as placeholder identifiers
                new HardKey("Esc", 1, 0),
                new HardKey("F1", 1, 0),
                new HardKey("F2", 1, 0),
                new HardKey("F3", 1, 0),
                new HardKey("F4", 1, 0),
                new HardKey("F5", 1, 0),
                new HardKey("F6", 1, 0),
                new HardKey("F7", 1, 0),
                new HardKey("F8", 1, 0),
                new HardKey("F9", 1, 0),
                new HardKey("F10", 1, 0),
                new HardKey("F11", 1, 0),
                new HardKey("F12", 1, 0),
                new HardKey("Tmp", 1, 0),
                new HardKey("Tmp", 1, 0),
                new HardKey("Del", 1, 0),

                new HardKey("`", "~", 1, 0),
                new HardKey("1", "!", 1, 0),
                new HardKey("2", "@", 1, 0),
                new HardKey("3", "#", 1, 0),
                new HardKey("4", "$", 1, 0),
                new HardKey("5", "%", 1, 0),
                new HardKey("6", "^", 1, 0),
                new HardKey("7", "&", 1, 0),
                new HardKey("8", "*", 1, 0),
                new HardKey("9", "(", 1, 0),
                new HardKey("0", ")", 1, 0),
                new HardKey("-", "_", 1, 0),
                new HardKey("=", "+", 1, 0),
                new HardKey("Backspace", 2, 0),
                new HardKey("Home", 1, 0),

                new HardKey("Tab", 2, 0),
                new HardKey("q", "Q", 1, 0),
                new HardKey("w", "W", 1, 0),
                new HardKey("e", "E", 1, 0),
                new HardKey("r", "R", 1, 0),
                new HardKey("t", "T", 1, 0),
                new HardKey("y", "Y", 1, 0),
                new HardKey("u", "U", 1, 0),
                new HardKey("i", "I", 1, 0),
                new HardKey("o", "O", 1, 0),
                new HardKey("p", "P", 1, 0),
                new HardKey("[", "{", 1, 0),
                new HardKey("]", ")", 1, 0),
                new HardKey("\\", "|", 1, 0),
                new HardKey("End", 1, 0),

                new HardKey("Caps", 2, 0),
                new HardKey("a", "A", 1, 0),
                new HardKey("s", "S", 1, 0),
                new HardKey("d", "D", 1, 0),
                new HardKey("f", "F", 1, 0),
                new HardKey("g", "G", 1, 0),
                new HardKey("h", "H", 1, 0),
                new HardKey("j", "J", 1, 0),
                new HardKey("k", "K", 1, 0),
                new HardKey("l", "L", 1, 0),
                new HardKey(";", ":", 1, 0),
                new HardKey("'", "\"", 1, 0),
                new HardKey("Enter", 2, 0),
                new HardKey("Pg Up", 1, 0),

                new HardKey("Shift", 1, 0),
                new HardKey("z", "Z", 1, 0),
                new HardKey("x", "X", 1, 0),
                new HardKey("c", "C", 1, 0),
                new HardKey("v", "V", 1, 0),
                new HardKey("b", "B", 1, 0),
                new HardKey("n", "N", 1, 0),
                new HardKey("m", "M", 1, 0),
                new HardKey(",", "<", 1, 0),
                new HardKey(".", ">", 1, 0),
                new HardKey("/", "?", 1, 0),
                new HardKey("Shift", 2, 0),
                new HardKey("Up", 1, 0),
                new HardKey("Pg Dn", 1, 0),

                new HardKey("Ctrl", 1, 0),
                new HardKey("Win", 1, 0),
                new HardKey("Alt", 1, 0),
                new HardKey("Space", 7, 0),
                new HardKey("Alt", 1, 0),
                new HardKey("Fn", 1, 0),
                new HardKey("Ctrl", 1, 0),
                new HardKey("Left", 1, 0),
                new HardKey("Down", 1, 0),
                new HardKey("Right", 1, 0)
        };

        keys = init;
    }

    private class HardKey extends Button {
        public String name1, name2;
        public int width, identifier;

        // Constructor for a key with no second purpose
        // identifier is just a placeholder value to determine what to send to the Keyboard Writer
        public HardKey(String name, int width, int identifier) {
            this(name, "", width, identifier);
        }

        // Constructor for dual purpose keys that will be toggled by shift
        // If the identifier is the same,
        public HardKey(String name1, String name2, int width, int identifier) {
            super(HardKeyboardActivity.this);
            this.name1 = name1;
            this.name2 = name2;
            this.width = width;
            this.identifier = identifier;
        }

        @Override
        public void onDraw(Canvas canvas) {
            // Will draw the button to look more like a key
            // Will draw the text to reflect the state of shift
            super.onDraw(canvas);
        }

    }

}
