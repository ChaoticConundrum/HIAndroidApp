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

    private KeyboardWriter keyboardWriter = null;

    public HardKeyboardActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardkeyboard);

        keyboardWriter = new KeyboardWriter();

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

                        keyboardWriter.setKey(key.identifier, true);

                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        // Key released
                        Log.d("[HardKeyboard]", key.name1 + " released");
                        if (key.name1.equals("Shift")) {
                            shift = false;
                            for (HardKey key2 : keys) key2.updateKey();
                        }

                        keyboardWriter.setKey(key.identifier, false);

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
                new HardKey("Esc",  1, ScanCoder.Key.K_ESC),
                new HardKey("F1",   1, ScanCoder.Key.K_F1),
                new HardKey("F2",   1, ScanCoder.Key.K_F2),
                new HardKey("F3",   1, ScanCoder.Key.K_F3),
                new HardKey("F4",   1, ScanCoder.Key.K_F4),
                new HardKey("F5",   1, ScanCoder.Key.K_F5),
                new HardKey("F6",   1, ScanCoder.Key.K_F6),
                new HardKey("F7",   1, ScanCoder.Key.K_F7),
                new HardKey("F8",   1, ScanCoder.Key.K_F8),
                new HardKey("F9",   1, ScanCoder.Key.K_F9),
                new HardKey("F10",  1, ScanCoder.Key.K_F10),
                new HardKey("F11",  1, ScanCoder.Key.K_F11),
                new HardKey("F12",  1, ScanCoder.Key.K_F12),
                new HardKey("Tmp",  1, ScanCoder.Key.K_ESC),
                new HardKey("Tmp",  1, ScanCoder.Key.K_ESC),
                new HardKey("Del",  1, ScanCoder.Key.K_DEL),

                new HardKey("`", "~", 1, ScanCoder.Key.K_TILDE),
                new HardKey("1", "!", 1, ScanCoder.Key.K_1),
                new HardKey("2", "@", 1, ScanCoder.Key.K_2),
                new HardKey("3", "#", 1, ScanCoder.Key.K_3),
                new HardKey("4", "$", 1, ScanCoder.Key.K_4),
                new HardKey("5", "%", 1, ScanCoder.Key.K_5),
                new HardKey("6", "^", 1, ScanCoder.Key.K_6),
                new HardKey("7", "&", 1, ScanCoder.Key.K_7),
                new HardKey("8", "*", 1, ScanCoder.Key.K_8),
                new HardKey("9", "(", 1, ScanCoder.Key.K_9),
                new HardKey("0", ")", 1, ScanCoder.Key.K_0),
                new HardKey("-", "_", 1, ScanCoder.Key.K_DASH),
                new HardKey("=", "+", 1, ScanCoder.Key.K_EQ_PLUS),
                new HardKey("Backspace", 2, ScanCoder.Key.K_DEL),
                new HardKey("Home", 1,   ScanCoder.Key.K_HOME),

                new HardKey("Tab", 2,    ScanCoder.Key.K_TAB),
                new HardKey("q", "Q", 1, ScanCoder.Key.K_Q),
                new HardKey("w", "W", 1, ScanCoder.Key.K_W),
                new HardKey("e", "E", 1, ScanCoder.Key.K_E),
                new HardKey("r", "R", 1, ScanCoder.Key.K_R),
                new HardKey("t", "T", 1, ScanCoder.Key.K_T),
                new HardKey("y", "Y", 1, ScanCoder.Key.K_Y),
                new HardKey("u", "U", 1, ScanCoder.Key.K_U),
                new HardKey("i", "I", 1, ScanCoder.Key.K_I),
                new HardKey("o", "O", 1, ScanCoder.Key.K_O),
                new HardKey("p", "P", 1, ScanCoder.Key.K_P),
                new HardKey("[", "{", 1, ScanCoder.Key.K_OPENB),
                new HardKey("]", ")", 1, ScanCoder.Key.K_CLOSEB),
                new HardKey("\\", "|", 1, ScanCoder.Key.K_BACK_BAR),
                new HardKey("End", 1,    ScanCoder.Key.K_END),

                new HardKey("Caps", 2,   ScanCoder.Key.K_CAPS),
                new HardKey("a", "A", 1, ScanCoder.Key.K_A),
                new HardKey("s", "S", 1, ScanCoder.Key.K_S),
                new HardKey("d", "D", 1, ScanCoder.Key.K_D),
                new HardKey("f", "F", 1, ScanCoder.Key.K_F),
                new HardKey("g", "G", 1, ScanCoder.Key.K_G),
                new HardKey("h", "H", 1, ScanCoder.Key.K_H),
                new HardKey("j", "J", 1, ScanCoder.Key.K_J),
                new HardKey("k", "K", 1, ScanCoder.Key.K_K),
                new HardKey("l", "L", 1, ScanCoder.Key.K_L),
                new HardKey(";", ":", 1, ScanCoder.Key.K_COLONS),
                new HardKey("'", "\"", 1, ScanCoder.Key.K_QUOTES),
                new HardKey("Enter", 2,  ScanCoder.Key.K_ENTER),
                new HardKey("Pg Up", 1,  ScanCoder.Key.K_PGUP),

                new HardKey("Shift", 2, ScanCoder.Key.K_LSHIFT),
                new HardKey("z", "Z", 1, ScanCoder.Key.K_Z),
                new HardKey("x", "X", 1, ScanCoder.Key.K_X),
                new HardKey("c", "C", 1, ScanCoder.Key.K_C),
                new HardKey("v", "V", 1, ScanCoder.Key.K_V),
                new HardKey("b", "B", 1, ScanCoder.Key.K_B),
                new HardKey("n", "N", 1, ScanCoder.Key.K_N),
                new HardKey("m", "M", 1, ScanCoder.Key.K_M),
                new HardKey(",", "<", 1, ScanCoder.Key.K_COMMA),
                new HardKey(".", ">", 1, ScanCoder.Key.K_PERIOD),
                new HardKey("/", "?", 1, ScanCoder.Key.K_SLASH),
                new HardKey("Shift", 2, ScanCoder.Key.K_RSHIFT),
                new HardKey("Up", 1, ScanCoder.Key.K_UP),
                new HardKey("Pg Dn", 1, ScanCoder.Key.K_PGUP),

                new HardKey("Ctrl", 1,  ScanCoder.Key.K_LCTRL),
                new HardKey("Win",  1,  ScanCoder.Key.K_LMETA),
                new HardKey("Alt",  1,  ScanCoder.Key.K_LALT),
                new HardKey("Space", 7, ScanCoder.Key.K_SPACE),
                new HardKey("Alt",  1,  ScanCoder.Key.K_RALT),
                new HardKey("Fn",   1,  ScanCoder.Key.K_ESC),
                new HardKey("Ctrl", 1,  ScanCoder.Key.K_RCTRL),
                new HardKey("Left", 1,  ScanCoder.Key.K_LEFT),
                new HardKey("Down", 1,  ScanCoder.Key.K_DOWN),
                new HardKey("Right", 1, ScanCoder.Key.K_RIGHT),
        };

        keys = init;
    }

    private class HardKey extends Button {
        private String name1, name2, currentText;
        private int width;
        private ScanCoder.Key identifier;
        private Paint paint;

        public HardKey(String name, int width, ScanCoder.Key identifier) {
            this(name, name, width, identifier);
        }

        public HardKey(String name1, String name2, int width, ScanCoder.Key identifier) {
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
