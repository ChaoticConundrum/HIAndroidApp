package com.hiandroid.app;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class EditMacroActivity extends ListActivity {

    private MacroDatabase database;
    private Macro macro;

    private EditMacroAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_macro);

        database = new MacroDatabase(new MacroDatabaseHelper(this));
        macro = database.getMacros().get(getIntent().getIntExtra("MACRO_ID", 1)-1);

        TextView title = (TextView) findViewById(R.id.edit_macro_title);
        title.setText(macro.name);

        ImageButton discardButton = (ImageButton) findViewById(R.id.discard_button);
        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditMacroActivity.this);
                dialog.setMessage("Are you sure you want to discard any changes?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitActivity();
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
        ImageButton saveButton = (ImageButton) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().getCurrentFocus().clearFocus();
                database.saveMacrosToDB(database.getMacros());
                exitActivity();
            }
        });

        setListAdapter(new EditMacroAdapter(this, R.layout.edit_macro_list_item, macro));
    }

    private void exitActivity() {
        startActivity(new Intent(this, HardKeyboardActivity.class));
    }
}
