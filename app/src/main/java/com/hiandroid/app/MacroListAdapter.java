package com.hiandroid.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MacroListAdapter extends ArrayAdapter<Macro> {

    private ArrayList<Macro> macros;
    private KeyboardWriter keyboardWriter = null;
    private MacroListFragment fragment = null;

    public MacroListAdapter(Context context, int resource, KeyboardWriter keyboardWriter, MacroListFragment macroListFragment, ArrayList<Macro> macros) {
        super(context, resource);
        this.keyboardWriter = keyboardWriter;
        this.fragment = macroListFragment;
        this.macros = macros;
    }

    @Nullable
    @Override
    public Macro getItem(int index) {
        return macros.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public int getCount() {
        return macros.size();
    }

    @NonNull
    @Override
    public View getView(int index, View view, ViewGroup parent) {
        ViewHolder vh;
        final Macro macro = getItem(index);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.macro_list_item, parent, false);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        vh.label.setText(macro.name);

        vh.executeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // execute macro
                fragment.scheduleMacro(macro);
            }
        });
        vh.fastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // fast execute macro
                fragment.scheduleMacro(macro.getFast());
            }
        });
        vh.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete macro
                fragment.removeMacro(macro);
            }
        });

        return view;
    }

    private static class ViewHolder {
        ImageButton deleteButton;
        ImageButton fastButton;
        ImageButton executeButton;
        TextView label;
        View view;

        ViewHolder(View view) {
            deleteButton = (ImageButton) view.findViewById(R.id.delete_button);
            fastButton = (ImageButton) view.findViewById(R.id.fast_button);
            executeButton = (ImageButton) view.findViewById(R.id.execute_button);
            label = (TextView) view.findViewById(R.id.macro_label);
            this.view = view;
        }
    }
}
