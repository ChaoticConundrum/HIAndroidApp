package com.hiandroid.app;

import java.util.ArrayList;

public class Macro {

    public String name;
    public int id;
    public ArrayList<Long> times;
    public ArrayList<Integer> keys;
    public ArrayList<Boolean> states;

    public Macro(int id) {
        this(id, new ArrayList(), new ArrayList(), new ArrayList());
    }

    public Macro(int id, ArrayList times, ArrayList keys, ArrayList states) {
        this.name = "Macro " + id;
        this.id = id;
        this.times = times;
        this.keys = keys;
        this.states = states;
    }

    public Macro getFast(){
        ArrayList<Long> ntimes = new ArrayList<>();
        for(long i = 0 ; i < times.size(); ++i){
            ntimes.add(i * 10);
        }
        return new Macro(id, ntimes, keys, states);
    }

    class DatabaseEntry {
        public static final String TABLE_NAME = "macros";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_KEY = "key";
        public static final String COLUMN_NAME_STATE = "state";
    }
}