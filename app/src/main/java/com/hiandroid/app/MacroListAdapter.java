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

    private KeyboardWriter keyboardWriter = null;
    private ArrayList<Macro> macros;

    public MacroListAdapter(Context context, int resource, KeyboardWriter keyboardWriter, ArrayList<Macro> macros) {
        super(context, resource);
        this.keyboardWriter = keyboardWriter;
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
                new ExecuteMacroTask(keyboardWriter).execute(macro);
            }
        });
        vh.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // edit function
            }
        });

        return view;
    }

    private static class ViewHolder {
        ImageButton editButton;
        ImageButton executeButton;
        TextView label;
        View view;
        ViewHolder(View view) {
            editButton = (ImageButton) view.findViewById(R.id.edit_button);
            executeButton = (ImageButton) view.findViewById(R.id.execute_button);
            label = (TextView) view.findViewById(R.id.macro_label);
            this.view = view;
        }
    }
}
