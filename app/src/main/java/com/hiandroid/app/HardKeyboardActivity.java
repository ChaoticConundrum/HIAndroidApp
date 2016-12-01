package com.hiandroid.app;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

        shift = false;
        generateKeys();

        keyboardGrid = (GridLayout) findViewById(R.id.keyboardGrid);
        keyboardGrid.setColumnCount(16);
        keyboardGrid.setRowCount(6);
        keyboardGrid.setPadding(0,0,0,0);
        keyboardGrid.setPaddingRelative(0,0,0,0);

        for (final HardKey key : keys) {
            //key.setText(key.lowername);
            key.setPadding(0, 0, 0, 0);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, key.width);
            params.width = key.width * keyWidth;
            params.height = keyWidth;
            key.setLayoutParams(params);

            key.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        // Key pressed down
                        //Log.d("[HardKeyboard]", key.currentText + " pressed");

                        if (key.identifier == ScanCoder.Key.K_CAPS ||
                                key.identifier == ScanCoder.Key.K_LSHIFT ||
                                key.identifier == ScanCoder.Key.K_RSHIFT){
                            shift = !shift;
                            for (HardKey key2 : keys) key2.updateKey();
                        }

                        keyboardWriter.setKey(key.identifier, true);

                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        // Key released
                        //Log.d("[HardKeyboard]", key.lowername + " released");

                        if (key.identifier == ScanCoder.Key.K_LSHIFT ||
                                key.identifier == ScanCoder.Key.K_RSHIFT) {
                            shift = !shift;
                            for (HardKey key2 : keys) key2.updateKey();
                        }

                        keyboardWriter.setKey(key.identifier, false);
                    }
                    return false;
                }
            });

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
                new HardKey(ScanCoder.Key.K_ESC,        "Esc",  1),
                new HardKey(ScanCoder.Key.K_F1,         "F1",   1),
                new HardKey(ScanCoder.Key.K_F2,         "F2",   1),
                new HardKey(ScanCoder.Key.K_F3,         "F3",   1),
                new HardKey(ScanCoder.Key.K_F4,         "F4",   1),
                new HardKey(ScanCoder.Key.K_F5,         "F5",   1),
                new HardKey(ScanCoder.Key.K_F6,         "F6",   1),
                new HardKey(ScanCoder.Key.K_F7,         "F7",   1),
                new HardKey(ScanCoder.Key.K_F8,         "F8",   1),
                new HardKey(ScanCoder.Key.K_F9,         "F9",   1),
                new HardKey(ScanCoder.Key.K_F10,        "F10",  1),
                new HardKey(ScanCoder.Key.K_F11,        "F11",  1),
                new HardKey(ScanCoder.Key.K_F12,        "F12",  1),
                new HardKey(ScanCoder.Key.K_ESC,        "Tmp",  1),
                new HardKey(ScanCoder.Key.K_ESC,        "Tmp",  1),
                new HardKey(ScanCoder.Key.K_DEL,        "Del",  1),

                new HardKey(ScanCoder.Key.K_TILDE,      "`", "~", 1),
                new HardKey(ScanCoder.Key.K_1,          "1", "!", 1),
                new HardKey(ScanCoder.Key.K_2,          "2", "@", 1),
                new HardKey(ScanCoder.Key.K_3,          "3", "#", 1),
                new HardKey(ScanCoder.Key.K_4,          "4", "$", 1),
                new HardKey(ScanCoder.Key.K_5,          "5", "%", 1),
                new HardKey(ScanCoder.Key.K_6,          "6", "^", 1),
                new HardKey(ScanCoder.Key.K_7,          "7", "&", 1),
                new HardKey(ScanCoder.Key.K_8,          "8", "*", 1),
                new HardKey(ScanCoder.Key.K_9,          "9", "(", 1),
                new HardKey(ScanCoder.Key.K_0,          "0", ")", 1),
                new HardKey(ScanCoder.Key.K_DASH,       "-", "_", 1),
                new HardKey(ScanCoder.Key.K_EQ_PLUS,    "=", "+", 1),
                new HardKey(ScanCoder.Key.K_BACKSP,     "Backspace", 2),
                new HardKey(ScanCoder.Key.K_HOME,       "Home", 1),

                new HardKey(ScanCoder.Key.K_TAB,        "Tab", 2),
                new HardKey(ScanCoder.Key.K_Q,          "q", "Q", 1),
                new HardKey(ScanCoder.Key.K_W,          "w", "W", 1),
                new HardKey(ScanCoder.Key.K_E,          "e", "E", 1),
                new HardKey(ScanCoder.Key.K_R,          "r", "R", 1),
                new HardKey(ScanCoder.Key.K_T,          "t", "T", 1),
                new HardKey(ScanCoder.Key.K_Y,          "y", "Y", 1),
                new HardKey(ScanCoder.Key.K_U,          "u", "U", 1),
                new HardKey(ScanCoder.Key.K_I,          "i", "I", 1),
                new HardKey(ScanCoder.Key.K_O,          "o", "O", 1),
                new HardKey(ScanCoder.Key.K_P,          "p", "P", 1),
                new HardKey(ScanCoder.Key.K_OPENB,      "[", "{", 1),
                new HardKey(ScanCoder.Key.K_CLOSEB,     "]", ")", 1),
                new HardKey(ScanCoder.Key.K_BACK_BAR,   "\\", "|", 1),
                new HardKey(ScanCoder.Key.K_END,        "End", 1),

                new HardKey(ScanCoder.Key.K_CAPS,       "Caps", 2),
                new HardKey(ScanCoder.Key.K_A,          "a", "A", 1),
                new HardKey(ScanCoder.Key.K_S,          "s", "S", 1),
                new HardKey(ScanCoder.Key.K_D,          "d", "D", 1),
                new HardKey(ScanCoder.Key.K_F,          "f", "F", 1),
                new HardKey(ScanCoder.Key.K_G,          "g", "G", 1),
                new HardKey(ScanCoder.Key.K_H,          "h", "H", 1),
                new HardKey(ScanCoder.Key.K_J,          "j", "J", 1),
                new HardKey(ScanCoder.Key.K_K,          "k", "K", 1),
                new HardKey(ScanCoder.Key.K_L,          "l", "L", 1),
                new HardKey(ScanCoder.Key.K_COLONS,     ";", ":", 1),
                new HardKey(ScanCoder.Key.K_QUOTES,     "'", "\"", 1),
                new HardKey(ScanCoder.Key.K_ENTER,      "Enter", 2),
                new HardKey(ScanCoder.Key.K_PGUP,       "Pg Up", 1),

                new HardKey(ScanCoder.Key.K_LSHIFT,     "Shift", 2),
                new HardKey(ScanCoder.Key.K_Z,          "z", "Z", 1),
                new HardKey(ScanCoder.Key.K_X,          "x", "X", 1),
                new HardKey(ScanCoder.Key.K_C,          "c", "C", 1),
                new HardKey(ScanCoder.Key.K_V,          "v", "V", 1),
                new HardKey(ScanCoder.Key.K_B,          "b", "B", 1),
                new HardKey(ScanCoder.Key.K_N,          "n", "N", 1),
                new HardKey(ScanCoder.Key.K_M,          "m", "M", 1),
                new HardKey(ScanCoder.Key.K_COMMA,      ",", "<", 1),
                new HardKey(ScanCoder.Key.K_PERIOD,     ".", ">", 1),
                new HardKey(ScanCoder.Key.K_SLASH,      "/", "?", 1),
                new HardKey(ScanCoder.Key.K_RSHIFT,     "Shift", 2),
                new HardKey(ScanCoder.Key.K_UP,         "Up", 1),
                new HardKey(ScanCoder.Key.K_PGDN,       "Pg Dn", 1),

                new HardKey(ScanCoder.Key.K_LCTRL,      "Ctrl", 1),
                new HardKey(ScanCoder.Key.K_LMETA,      "Win",  1),
                new HardKey(ScanCoder.Key.K_LALT,       "Alt",  1),
                new HardKey(ScanCoder.Key.K_SPACE,      "Space", 7),
                new HardKey(ScanCoder.Key.K_RALT,       "Alt",  1),
                new HardKey(ScanCoder.Key.K_ESC,        "Fn",   1),
                new HardKey(ScanCoder.Key.K_RCTRL,      "Ctrl", 1),
                new HardKey(ScanCoder.Key.K_LEFT,       "Left", 1),
                new HardKey(ScanCoder.Key.K_DOWN,       "Down", 1),
                new HardKey(ScanCoder.Key.K_RIGHT,      "Right", 1),
        };

        keys = init;
    }

    private class HardKey extends Button {
        private ScanCoder.Key identifier;
        private String lowername, uppername;
        private int width;
        private Paint paint;

        public HardKey(ScanCoder.Key identifier, String name, int width) {
            this(identifier, name, name, width);
        }

        public HardKey(ScanCoder.Key identifier, String lowername, String uppername, int width) {
            super(HardKeyboardActivity.this);
            this.lowername = lowername;
            this.uppername = uppername;
            this.width = width;
            this.identifier = identifier;
            paint = new Paint();
            updateKey();
        }

        public void updateKey() {
            if (shift) {
                setText(uppername);
            } else {
                setText(lowername);
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
