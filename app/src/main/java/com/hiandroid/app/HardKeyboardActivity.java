package com.hiandroid.app;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

public class HardKeyboardActivity extends Activity {

    private GridLayout keyboardGrid;
    private HardKey[] keys;
    private boolean shift;
    private Typeface textFont;

    public HardKeyboardActivity() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardkeyboard);

        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        int keyWidth = (display.widthPixels / 16);

        //textFont = new Typeface();

        generateKeys();

        keyboardGrid = (GridLayout) findViewById(R.id.keyboardGrid);
        keyboardGrid.setColumnCount(16);
        keyboardGrid.setRowCount(6);
        keyboardGrid.setPadding(0,0,0,0);
        keyboardGrid.setPaddingRelative(0,0,0,0);
        for (final HardKey key : keys) {
            key.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        // Key pressed down
                        Log.d("[HardKeyboard]", key.currentText + " pressed");
                        if (key.name1.equals("Shift")) {
                            shift = true;
                            for (HardKey key2 : keys) key2.updateKey();
                        }
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        // Key released
                        Log.d("[HardKeyboard]", key.name1 + " released");
                        if (key.name1.equals("Shift")) {
                            shift = false;
                            for (HardKey key2 : keys) key2.updateKey();
                        }
                        //return true;
                    }
                    return false;
                }
            });
            key.setText(key.name1);
            key.setPadding(0, 0, 0, 0);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, key.width);
            params.width = key.width * keyWidth;
            params.height = keyWidth;
            key.setLayoutParams(params);
            keyboardGrid.addView(key);
        }

        RelativeLayout frame = (RelativeLayout) findViewById(R.id.relLayout);

        RelativeLayout.LayoutParams reparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        reparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //frame.setLayoutParams(reparams);
        //frame.setLayoutParams(reparams);
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

                new HardKey("Shift", 2, 0),
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
        private String name1, name2, currentText;
        private int width, identifier;
        private Paint paint;

        public HardKey(String name, int width, int identifier) {
            this(name, name, width, identifier);
        }

        public HardKey(String name1, String name2, int width, int identifier) {
            super(HardKeyboardActivity.this);
            this.name1 = name1;
            this.name2 = name2;
            this.width = width;
            this.identifier = identifier;
            paint = new Paint();
            currentText = name1;
        }

        public void updateKey() {
            if (shift) {
                if (currentText.equals(name1)) {
                    setText(name2);
                    currentText = name2;
                }
            } else if (currentText.equals(name2)) {
                setText(name1);
                currentText = name1;
            }
        }

        @Override
        public void onDraw(Canvas canvas) {
            // Will draw the button to look more like a key
            // Will draw the text to reflect the state of shift
            //super.onDraw(canvas);

            paint.setColor(Color.parseColor("#232323"));
            paint.setStrokeWidth(3);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(2,2, getWidth()-4, getHeight()-4, paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor((isPressed()) ?  "#ababab" : "#eaeaea"));
            canvas.drawRect(5,5, getWidth()-7, getHeight()-7, paint);

            paint.setColor(Color.parseColor("#232323"));
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(36);

            int xPos = (canvas.getWidth() / 2);
            int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ;

            canvas.drawText(getText().toString(), xPos, yPos, paint);

        }

    }

}
