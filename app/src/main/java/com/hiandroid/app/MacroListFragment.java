package com.hiandroid.app;

import android.app.ListFragment;
import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MacroListFragment extends ListFragment {

    public ArrayList<Macro> macros;

    private ArrayAdapter<Macro> adapter;
    private MacroDatabase macroDatabase;
    public Timer macroTimer;

    private KeyboardWriter keyboardWriter = null;

    public MacroListFragment() {
        macroTimer = new Timer();
    }

    public void setKeyboardWriter(KeyboardWriter keyboardWriter) {
        this.keyboardWriter = keyboardWriter;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Open database and load macros
        macroDatabase = new MacroDatabase(new MacroDatabaseHelper(getContext()));
        macros = macroDatabase.loadMacrosFromDB();

        adapter = new MacroListAdapter(getContext(), R.layout.macro_list_item, keyboardWriter, this, macros);
        setListAdapter(adapter);
    }

    public void addMacro(Macro m) {
        macros.add(m);
        adapter.notifyDataSetChanged();
        macroDatabase.saveMacrosToDB(macros);
    }

    public void removeMacro(Macro m){
        macros.remove(m);
        adapter.notifyDataSetChanged();
        macroDatabase.saveMacrosToDB(macros);
    }

    public void scheduleMacro(final Macro macro){
        Log.d("[MacroListAdapter]", "Schedule " + macro.name);
        for(int i = 0 ; i < macro.times.size(); ++i){
            final int j = i;
            macroTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Log.d("[MacroListAdapter]", "Key " + macro.keys.get(j) + " is " + macro.states.get(j));
                    keyboardWriter.setMacroKey(macro.keys.get(j), macro.states.get(j));
                }
            }, macro.times.get(i));
        }
    }
}
