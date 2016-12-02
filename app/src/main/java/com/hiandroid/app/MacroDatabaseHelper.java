package com.hiandroid.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MacroDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Macros.db";
    private static final int DB_VERSION = 1;

    private static final String SQL_CREATE_MACROS_TABLE =
            "CREATE TABLE " + Macro.DatabaseEntry.TABLE_NAME + "(" +
                    Macro.DatabaseEntry.COLUMN_NAME_NAME + " VARCHAR PRIMARY KEY, " +
                    Macro.DatabaseEntry.COLUMN_NAME_TIME + " VARCHAR NOT NULL, " +
                    Macro.DatabaseEntry.COLUMN_NAME_KEY + " VARCHAR NOT NULL, " +
                    Macro.DatabaseEntry.COLUMN_NAME_STATE + " VARCHAR NOT NULL)";

    private static final String SQL_DROP_MACROS_TABLE =
            "DROP TABLE IF EXISTS " + Macro.DatabaseEntry.TABLE_NAME;


    public MacroDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MACROS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_MACROS_TABLE);
        db.execSQL(SQL_CREATE_MACROS_TABLE);
    }

    public void clearDatabase(SQLiteDatabase db) {
        db.execSQL(SQL_DROP_MACROS_TABLE);
        db.execSQL(SQL_CREATE_MACROS_TABLE);
    }
}
