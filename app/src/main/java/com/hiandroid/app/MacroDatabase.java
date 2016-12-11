package com.hiandroid.app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MacroDatabase implements Serializable {

    public static String serial = "MACRO_DATABASE";

    private MacroDatabaseHelper databaseHelper;
    private ArrayList<Macro> macros;

    public MacroDatabase(MacroDatabaseHelper databaseHelper){
        this.databaseHelper = databaseHelper;
        loadMacrosFromDB();
    }

    public ArrayList<Macro> getMacros() { return macros; }

    public void loadMacrosFromDB() {
        macros = new ArrayList();
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Cursor c = database.query(Macro.DatabaseEntry.TABLE_NAME, null, null, null, null, null, null);
        int idIndex = c.getColumnIndex(Macro.DatabaseEntry.COLUMN_NAME_ID);
        int timeIndex = c.getColumnIndex(Macro.DatabaseEntry.COLUMN_NAME_TIME);
        int keyIndex = c.getColumnIndex(Macro.DatabaseEntry.COLUMN_NAME_KEY);
        int stateIndex = c.getColumnIndex(Macro.DatabaseEntry.COLUMN_NAME_STATE);

        while (c.moveToNext()) {
            int id = c.getInt(idIndex);

            String[] timeStrings = c.getString(timeIndex).split(",");
            ArrayList<Long> times = new ArrayList();
            for (String a : timeStrings) times.add(Long.parseLong(a));

            String[] keyStrings = c.getString(keyIndex).split(",");
            ArrayList<Integer> keys = new ArrayList();
            for (String a : keyStrings) keys.add(Integer.parseInt(a));

            String[] stateStrings = c.getString(stateIndex).split(",");
            ArrayList<Boolean> states = new ArrayList();
            for (String a : stateStrings) states.add(Boolean.parseBoolean(a));

            macros.add(new Macro(id, times, keys, states));
        }
    }

    public void saveMacrosToDB(ArrayList<Macro> macros) {
        this.macros = macros;
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        databaseHelper.clearDatabase(database);
        ContentValues values = new ContentValues();

        for (int i = 0; i < macros.size(); i++) {
            Macro m = macros.get(i);

            String times = m.times.toString().replace("[", "").replace("]", "").replace(", ", ",");
            String keys =  m.keys.toString().replace("[", "").replace("]", "").replace(", ", ",");
            String states =  m.states.toString().replace("[", "").replace("]", "").replace(", ", ",");

            values.put(Macro.DatabaseEntry.COLUMN_NAME_ID, m.id);
            values.put(Macro.DatabaseEntry.COLUMN_NAME_TIME, times);
            values.put(Macro.DatabaseEntry.COLUMN_NAME_KEY, keys);
            values.put(Macro.DatabaseEntry.COLUMN_NAME_STATE, states);

            database.insert(Macro.DatabaseEntry.TABLE_NAME, null, values);
        }
    }
}
