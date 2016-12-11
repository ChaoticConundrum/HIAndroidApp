package com.hiandroid.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class EditMacroAdapter extends ArrayAdapter<Long> {

    private Macro macro;

    public EditMacroAdapter(Context context, int resource, Macro macro) {
        super(context, resource);
        this.macro = macro;
    }

    @Nullable
    @Override
    public Long getItem(int index) {
        return macro.times.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public int getCount() {
        return macro.times.size();
    }

    @NonNull
    @Override
    public View getView(final int index, View view, ViewGroup parent) {
        final ViewHolder vh;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.edit_macro_list_item, parent, false);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        vh.time.setText(Long.toString(macro.times.get(index)));
        vh.time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) macro.times.set(index, Long.parseLong(vh.time.getText().toString()));
            }
        });
        vh.scan.setText(Integer.toString(macro.keys.get(index)));
        vh.scan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) macro.keys.set(index, Integer.parseInt(vh.scan.getText().toString()));
            }
        });
        vh.state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                macro.states.set(index, isChecked);
            }
        });
        vh.state.setChecked(macro.states.get(index));

        return view;
    }

    private static class ViewHolder {
        EditText time, scan;
        CheckBox state;
        View view;

        ViewHolder(View view) {
            time = (EditText) view.findViewById(R.id.edit_macro_time);
            scan = (EditText) view.findViewById(R.id.edit_macro_scan);
            state = (CheckBox) view.findViewById(R.id.edit_macro_state);
            this.view = view;
        }
    }
}