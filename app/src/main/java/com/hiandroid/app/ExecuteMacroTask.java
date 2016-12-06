package com.hiandroid.app;

import android.os.AsyncTask;
import android.util.Log;

public class ExecuteMacroTask extends AsyncTask<Macro, Void, Void> {

    private KeyboardWriter keyboardWriter;

    public ExecuteMacroTask(KeyboardWriter keyboardWriter) {
        this.keyboardWriter = keyboardWriter;
    }

    protected Void doInBackground(Macro... macros) {
        Macro m = macros[0];
        Log.d("[MacroTask]", "Start " + m.name);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < m.times.size(); i++) {
            long nextTime = m.times.get(i);
            Log.d("[MacroTask]", "Key " + m.keys.get(i) + " is " + m.states.get(i) + " at time " + nextTime);
            try {
                long ms = nextTime - (System.currentTimeMillis() - startTime);
                if(ms > 0) {
                    Thread.sleep(ms);
                }

                keyboardWriter.setMacroKey(m.keys.get(i), m.states.get(i));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d("[MacroTask]", "End " + m.name);
        return null;
    }

}
