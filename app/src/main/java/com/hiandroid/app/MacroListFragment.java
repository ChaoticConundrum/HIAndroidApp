package com.hiandroid.app;

import android.app.ListFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MacroListFragment extends ListFragment {

    public ArrayList<Macro> macros;
    private ArrayAdapter<Macro> adapter;
    private MacroDatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public MacroListFragment() {  }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        databaseHelper = new MacroDatabaseHelper(getContext());

        loadMacrosFromDB();

        adapter = new MacroListAdapter(getContext(), R.layout.macro_list_item, macros);
        setListAdapter(adapter);
    }

    public void addMacro(Macro m) {
        macros.add(m);
        adapter.notifyDataSetChanged();
        saveMacrosToDB();
    }

    private void loadMacrosFromDB() {
        macros = new ArrayList();
        database = databaseHelper.getReadableDatabase();

        String[] form = {
                Macro.DatabaseEntry.COLUMN_NAME_NAME,
                Macro.DatabaseEntry.COLUMN_NAME_TIME,
                Macro.DatabaseEntry.COLUMN_NAME_KEY,
                Macro.DatabaseEntry.COLUMN_NAME_STATE,
        };
        Cursor c = database.query(Macro.DatabaseEntry.TABLE_NAME, null, null, null, null, null, null);
        int nameIndex = c.getColumnIndex(Macro.DatabaseEntry.COLUMN_NAME_NAME);
        int timeIndex = c.getColumnIndex(Macro.DatabaseEntry.COLUMN_NAME_TIME);
        int keyIndex = c.getColumnIndex(Macro.DatabaseEntry.COLUMN_NAME_KEY);
        int stateIndex = c.getColumnIndex(Macro.DatabaseEntry.COLUMN_NAME_STATE);

        while (c.moveToNext()) {
            String name = c.getString(nameIndex);

            String[] timeStrings = c.getString(timeIndex).split(",");
            ArrayList<Long> times = new ArrayList();
            for (String a : timeStrings) times.add(Long.parseLong(a));

            String[] keyStrings = c.getString(keyIndex).split(",");
            ArrayList<Byte> keys = new ArrayList();
            for (String a : keyStrings) keys.add(Byte.parseByte(a));

            String[] stateStrings = c.getString(stateIndex).split(",");
            ArrayList<Boolean> states = new ArrayList();
            for (String a : stateStrings) states.add(Boolean.parseBoolean(a));

            macros.add(new Macro(name, times, keys, states));
        }

    }

    private void saveMacrosToDB() {
        database = databaseHelper.getWritableDatabase();
        databaseHelper.clearDatabase(database);
        ContentValues values = new ContentValues();

        for (int i = 0; i < macros.size(); i++) {
            Macro m = macros.get(i);

            String times = m.times.toString().replace("[", "").replace("]", "").replace(", ", ",");
            String keys =  m.keys.toString().replace("[", "").replace("]", "").replace(", ", ",");
            String states =  m.states.toString().replace("[", "").replace("]", "").replace(", ", ",");

            values.put(Macro.DatabaseEntry.COLUMN_NAME_NAME, m.name);
            values.put(Macro.DatabaseEntry.COLUMN_NAME_TIME, times);
            values.put(Macro.DatabaseEntry.COLUMN_NAME_KEY, keys);
            values.put(Macro.DatabaseEntry.COLUMN_NAME_STATE, states);

            database.insert(Macro.DatabaseEntry.TABLE_NAME, null, values);
        }

    }
}
