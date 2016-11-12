package com.hiandroid.app;

import android.util.Log;
import android.view.View;

public class Macro {

    private String name;
    // Chose to keep the click listeners stored in memory rather than creating them on the fly while recycling in the List Adapter
    private View.OnClickListener executeClickListener;
    private View.OnClickListener editClickListener;


    public Macro(String name) {
        this.name = name;
        executeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execute();
            }
        };
        editClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: determine the best way to communicate with the UI thread to switch activities
            }
        };
    }

    public String getName() {
        return name;
    }

    public View.OnClickListener getExecuteClickListener() {
        return executeClickListener;
    }

    public View.OnClickListener getEditClickListener() {
        return editClickListener;
    }

    public void execute() {
        Log.d("[Macro]", name + " was executed");
    }
}