package com.hiandroid.app;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ScanCoder {

    private Process process = null;
    private DataOutputStream cmdStream = null;

    public enum Key {
        K_A         (0x04),
        K_B         (0x05),
        K_C         (0x06),
        K_D         (0x07),
        K_E         (0x08),
        K_F         (0x09),
        K_G         (0x0a),
        K_H         (0x0b),
        K_I         (0x0c),
        K_J         (0x0d),
        K_K         (0x0e),
        K_L         (0x0f),
        K_M         (0x10),
        K_N         (0x11),
        K_O         (0x12),
        K_P         (0x13),
        K_Q         (0x14),
        K_R         (0x15),
        K_S         (0x16),
        K_T         (0x17),
        K_U         (0x18),
        K_V         (0x19),
        K_W         (0x1a),
        K_X         (0x1b),
        K_Y         (0x1c),
        K_Z         (0x1d),

        K_1         (0x1e),
        K_2         (0x1f),
        K_3         (0x20),
        K_4         (0x21),
        K_5         (0x22),
        K_6         (0x23),
        K_7         (0x24),
        K_8         (0x25),
        K_9         (0x26),
        K_0         (0x27),

        K_ENTER     (0x28),
        K_ESC       (0x29),
        K_BACKSP    (0x2a),
        K_TAB       (0x2b),
        K_SPACE     (0x2c),

        K_DASH      (0x2d),
        K_EQ_PLUS   (0x2e),
        K_OPENB     (0x2f),
        K_CLOSEB    (0x30),
        K_BACK_BAR  (0x31),
        K_COLONS    (0x33),
        K_QUOTES    (0x34),
        K_TILDE     (0x35),
        K_COMMA     (0x36),
        K_PERIOD    (0x37),
        K_SLASH     (0x38),

        K_CAPS      (0x39),

        K_F1        (0x3a),
        K_F2        (0x3b),
        K_F3        (0x3c),
        K_F4        (0x3d),
        K_F5        (0x3e),
        K_F6        (0x3f),
        K_F7        (0x40),
        K_F8        (0x41),
        K_F9        (0x42),
        K_F10       (0x43),
        K_F11       (0x44),
        K_F12       (0x45),

        K_PRNTSCR   (0x46),
        K_SCROLL    (0x47),
        K_PAUSE     (0x48),
        K_INSERT    (0x49),
        K_HOME      (0x4a),
        K_PGUP      (0x4b),
        K_DEL       (0x4c),
        K_END       (0x4d),
        K_PGDN      (0x4e),

        K_RIGHT     (0x4f),
        K_LEFT      (0x50),
        K_DOWN      (0x51),
        K_UP        (0x52),

        K_NUMLK     (0x53),
        K_NUMDIV    (0x54),
        K_NUMMUL    (0x55),
        L_NUMSUB    (0x56),
        K_NUMADD    (0x57),
        K_NUMENTER  (0x58),

        K_NUM1      (0x59),
        K_NUM2      (0x5a),
        K_NUM3      (0x5b),
        K_NUM4      (0x5c),
        K_NUM5      (0x5d),
        K_NUM6      (0x5e),
        K_NUM7      (0x5f),
        K_NUM8      (0x60),
        K_NUM9      (0x61),
        K_NUM0      (0x62),

        K_NUMDEC    (0x63),

        K_LCTRL     (0xe0),
        K_LSHIFT    (0xe1),
        K_LALT      (0xe2),
        K_LMETA     (0xe3),
        K_RCTRL     (0xe4),
        K_RSHIFT    (0xe5),
        K_RALT      (0xe6),
        K_RMETA     (0xe7);

        private final int scan;

        private static final Key[] codemap = new Key[256];

        Key(int code){
            this.scan = code;
        }

        public int code(){
            return scan;
        }

        public static Key codeKey(int code){
            return codemap[code];
        }

        static {
            // Map the codes to Keys
            for(Key k : Key.values()){
                codemap[k.scan] = k;
            }
        }
    }

    public ScanCoder(){
        // Open su shell
        startSu();
    }

    public void sendCodes(Key[] codes){
        if(codes == null)
            return;

        // Just make sure this is only happening on one thread at a time
        // Should be fine if that happens, even mashing keys while running a macro
        synchronized (this) {
            if (!isSuOpen()) {
                Log.d("[ScanCoder]", "su shell exited");
                // Re-open su shell
                startSu();
            }
            if (!isSuOpen())
                return;

            try {
                // Make BIOS report
                byte bytes[] = new byte[8];
                int j = 2;
                for (int i = 0; i < codes.length; ++i) {
                    if (codes[i].code() >= Key.K_LCTRL.code() &&
                            codes[i].code() <= Key.K_RMETA.code()) {
                        // OR in modifiers
                        bytes[0] |= 1 << (codes[i].code() - Key.K_LCTRL.code());
                    } else if (j >= 8) {
                        // Break on roll over
                        break;
                    } else {
                        // Max 6 normal keys
                        bytes[j++] = (byte)codes[i].code();
                    }
                }
                Log.d("[ScanCoder]", Arrays.toString(bytes));
                // Send report
                cmdStream.writeBytes("dd of=/dev/hidg0 bs=8 count=1\n");
                cmdStream.write(bytes);
                cmdStream.writeBytes("\n");
                cmdStream.flush();

//            // Make N-key report
//            bytes = new byte[30];
//            j = 0;
//            for (int i = 0; i < codes.length; ++i){
//                byte cd = codes[i].code();
//                bytes[(cd / 8) + 8] |= 1 << (cd % 8);
//            }
//            // Send report
//            cmdStream.writeBytes("dd of=/dev/hidg1 bs=30 count=1\n");
//            cmdStream.write(bytes);
//            cmdStream.writeBytes("\n");
//            cmdStream.flush();

            } catch (IOException e) {
                Log.e("[ScanCoder]", "Failed to write char device " + e.toString());
            }
        }
    }

    void startSu() {
        try {
            process = Runtime.getRuntime().exec("su");
            cmdStream = new DataOutputStream(process.getOutputStream());
            Log.d("[ScanCoder]", "su shell opened");
        } catch(IOException e) {
            Log.e("[ScanCoder]", "Failed to open su shell");
        }
    }

    boolean isSuOpen() {
        if(process == null || cmdStream == null)
            return true;
        // This is awful, but the only option
        try {
            process.exitValue();
            return false;
        } catch (IllegalThreadStateException e) {
            return true;
        }
    }
}
