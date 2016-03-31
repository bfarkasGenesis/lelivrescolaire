package com.lelivrescolaire.testtechnique.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 3/31/16.
 * Helper for database lifecycle
 */
public class CustomSQLHelper extends SQLiteOpenHelper {

    public static String DBNAME = "gpstracker_db";

    /**
     * Version number of the database
     */
    private static int VERSION = 1;

    private static CustomSQLHelper instance;

    public CustomSQLHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    public static synchronized CustomSQLHelper getHelper(Context context) {
        if (instance == null)
            instance = new CustomSQLHelper(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO when database update is needed
    }

    private void createDatabase(SQLiteDatabase db) {
        db.execSQL(BookDao.CREATE_TABLE);
        db.execSQL(PageDao.CREATE_TABLE);
    }
}