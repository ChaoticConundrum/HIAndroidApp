package com.hiandroid.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    private MacroListFragment fragment = null;
    private Context context;

    public MacroListAdapter(Context context, int resource, MacroListFragment macroListFragment, MacroDatabase database) {
        super(context, resource);
        this.context = context;
        this.fragment = macroListFragment;
        this.macros = database.getMacros();
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
        vh.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Edit Macro Activity
                Intent intent = new Intent(context, EditMacroActivity.class);
                intent.putExtra("MACRO_ID", macro.id);
                context.startActivity(intent);
            }
        });
        vh.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete macro
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setMessage("Are you sure you want to delete " + macro.name + "?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fragment.removeMacro(macro);
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        return view;
    }

    private static class ViewHolder {
        ImageButton deleteButton;
        ImageButton editButton;
        ImageButton fastButton;
        ImageButton executeButton;
        TextView label;
        View view;

        ViewHolder(View view) {
            deleteButton = (ImageButton) view.findViewById(R.id.delete_button);
            editButton = (ImageButton) view.findViewById(R.id.edit_button);
            fastButton = (ImageButton) view.findViewById(R.id.fast_button);
            executeButton = (ImageButton) view.findViewById(R.id.execute_button);
            label = (TextView) view.findViewById(R.id.macro_label);
            this.view = view;
        }
    }
}
