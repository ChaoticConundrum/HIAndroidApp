package com.hiandroid.app;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class Macro {

    public String name;

    public ArrayList<Long> times;
    public ArrayList<Byte> keys;
    public ArrayList<Boolean> states;

    private View.OnClickListener executeClickListener;
    private View.OnClickListener editClickListener;

    public Macro(String name) {
        this(name, new ArrayList(), new ArrayList(), new ArrayList());
    }

    public Macro(String name, ArrayList times, ArrayList keys, ArrayList states) {
        this.name = name;
        this.times = times;
        this.keys = keys;
        this.states = states;
    }

    public Macro getFast(){
        ArrayList<Long> ntimes = new ArrayList<>();
        for(long i = 0 ; i < times.size(); ++i){
            ntimes.add(i * 10);
        }
        return new Macro(name, ntimes, keys, states);
    }

    class DatabaseEntry {
        public static final String TABLE_NAME = "macros";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_KEY = "key";
        public static final String COLUMN_NAME_STATE = "state";
    }
}