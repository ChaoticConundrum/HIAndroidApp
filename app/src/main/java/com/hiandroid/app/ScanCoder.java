package com.hiandroid.app;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

public class ScanCoder {

    private Process process = null;
    private DataOutputStream cmdStream = null;

    public enum Keys {
        K_A     (0x04, 0x41),
        K_B     (0x05, 0x42),
        K_C     (0x06, 0x43),
        K_D     (0x07, 0x44),
        K_E     (0x08, 0x45),
        K_F     (0x09, 0x46),
        K_G     (0x0a, 0x47),
        K_H     (0x0b, 0x48),
        K_I     (0x0c, 0x49),
        K_J     (0x0d, 0x4a),
        K_K     (0x0e, 0x4b),
        K_L     (0x0f, 0x4c),
        K_M     (0x10, 0x4d),
        K_N     (0x11, 0x4e),
        K_O     (0x12, 0x4f),
        K_P     (0x13, 0x50),
        K_Q     (0x14, 0x51),
        K_R     (0x15, 0x52),
        K_S     (0x16, 0x53),
        K_T     (0x17, 0x54),
        K_U     (0x18, 0x55),
        K_V     (0x19, 0x56),
        K_W     (0x1a, 0x57),
        K_X     (0x1b, 0x58),
        K_Y     (0x1c, 0x59),
        K_Z     (0x1d, 0x5a),

        K_0     (0x37, 0x30),
        K_1     (0x1e, 0x31),
        K_2     (0x1f, 0x32),
        K_3     (0x20, 0x33),
        K_4     (0x21, 0x34),
        K_5     (0x22, 0x35),
        K_6     (0x23, 0x36),
        K_7     (0x24, 0x37),
        K_8     (0x25, 0x38),
        K_9     (0x26, 0x39),

        K_ENTER (0x28, 0x4d),
        K_ESC   (0x29),
        K_DEL   (0x2a, 0x4d),
        K_TAB   (0x2b, 0x4d),
        K_SPACE (0x2c, 0x4d),

        K_LCTRL (0xe0),
        K_LSHIFT(0xe1),
        K_LALT  (0xe2),
        K_LMETA (0xe3),
        K_RCTRL (0xe4),
        K_RSHIFT(0xe5),
        K_RALT  (0xe6),
        K_RMETA (0xe7);

        private final int value;
        private final int ascii;

        private Keys(int code){
            this.value = code;
            this.ascii = 0;
        }
        private Keys(int code, int ascii){
            this.value = code;
            this.ascii = ascii;
        }
        public byte code(){
            return (byte)value;
        }

        public static byte asciiCode(int ascii){
            return 0;
        }
    }

    public ScanCoder(){
        try {
            process = Runtime.getRuntime().exec("su");
            cmdStream = new DataOutputStream(process.getOutputStream());
        } catch(IOException e) {
            Log.e("[ScanCoder]", "Failed to open su shell");
        }
    }

    // Returns 2 bytes
    // The first byte contains modifier flags (shift only)
    // The second byte is the scan code for the symbol
    public static byte[] asciiToScan(int ascii){
        byte scan[] = new byte[2];
        if(ascii == 48){
            // Zero
            scan[0] = 0;
            scan[1] = 39;
        } else if(ascii >= 49 && ascii <= 57){
            // Numbers
            scan[0] = 0;
            scan[1] = (byte) (ascii - 49 + 30);
        } else if(ascii >= 65 && ascii <= 90) {
            // Uppercase alphas
            scan[0] = Keys.K_LSHIFT.code();
            scan[1] = (byte)(ascii - 65 + 4);
        } else if(ascii >= 97 && ascii <= 122) {
            // Lowercase alphas
            scan[0] = 0;
            scan[1] = (byte) (ascii - 97 + 4);
        } else {
            // Unknown
            Log.d("[ScanCoder]", "Unknown ASCII: " + ascii);
        }
        return scan;
    }

    public void sendCodes(byte[] codes){
        if(cmdStream == null || codes == null)
            return;
        try {
            // Make report
            byte bytes[] = new byte[8];
            int j = 2;
            for (int i = 0; i < codes.length; ++i){
                if(codes[i] >= 0xe0 && codes[i] <= 0xe7){
                    // OR in modifiers
                    bytes[0] |= 1 << (codes[i] - 0xe0);
                } else if(j < 8) {
                    // Max 6 normal keys
                    bytes[j++] = codes[i];
                }
            }
            // Send report
            String bystr = String.format(
                    "\\x%02x\\x%02x\\x%02x\\x%02x\\x%02x\\x%02x\\x%02x\\x%02x",
                    bytes[0], bytes[1], bytes[2], bytes[3], bytes[4], bytes[5], bytes[6], bytes[7]);
            //Log.d("[ScanCoder]", bystr);
            // Write report
            cmdStream.writeBytes("echo -n -e '" + bystr + "' > /dev/hidg0\n");
            cmdStream.flush();
        } catch(IOException e){
            Log.e("[ScanCoder]", "Failed to write char device " + e.toString());
        }
    }
}
