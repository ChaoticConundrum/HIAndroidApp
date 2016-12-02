package com.hiandroid.app;

import android.os.AsyncTask;
import android.util.Log;

public class ExecuteMacroTask extends AsyncTask<Macro, Void, Void> {

    private KeyboardWriter keyboardWriter;

    //public ExecuteMacroTask(KeyboardWriter keyboardWriter) {
    //    this.keyboardWriter = keyboardWriter;
    //}

    protected Void doInBackground(Macro... macros) {
        Macro m = macros[0];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < m.times.size(); i++) {
            long nextTime = m.times.get(i);
            Log.d("[MacroTask]", "Key " + m.keys.get(i) + " is " + m.states.get(i) + " at time " + nextTime);
            try {
                Thread.sleep((int)nextTime - (System.currentTimeMillis()-startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Need to be able to press a key from its scancode byte

            //keyboardWriter.setKey(m.keys.get(i), m.states.get(i));
        }
        return null;
    }

}
