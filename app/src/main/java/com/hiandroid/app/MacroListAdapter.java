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

    private MacroListFragment.OnListFragmentInteractionListener interactionListener;
    private ArrayList<Macro> macros;

    public MacroListAdapter(Context context, int resource, ArrayList<Macro> macros, MacroListFragment.OnListFragmentInteractionListener interactionListener) {
        super(context, resource);
        this.macros = macros;
        this.interactionListener = interactionListener;
    }

    public MacroListAdapter(Context context, int resource, ArrayList<Macro> macros) {
        super(context, resource);
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
        Macro macro = getItem(index);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.macro_list_item, parent, false);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        vh.label.setText(macro.getName());
        vh.executeButton.setOnClickListener(macro.getExecuteClickListener());
        return view;
    }

    /*
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != interactionListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    interactionListener.onListFragmentInteraction(getItem(position));
                }
            }
        });
    } */

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
