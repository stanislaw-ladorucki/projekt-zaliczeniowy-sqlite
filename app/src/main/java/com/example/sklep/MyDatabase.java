package com.example.sklep;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDatabase {

    private static DatabaseHelper helper;
    public static DatabaseHelper getHelper(Context context) {
        if (helper != null)
            return helper;
        else
            helper = new DatabaseHelper(context);
        return helper;
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "sklep.db";
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        private static final String CREATE_PRODUCTS_TABLE =
            "CREATE TABLE products (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "name TEXT NOT NULL," +
                    "price REAL" +
                    ")";

        private static final String DELETE_PRODUCTS_TABLE =
                "DROP TABLE IF EXISTS products";

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_PRODUCTS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL(DELETE_PRODUCTS_TABLE);
            db.execSQL(CREATE_PRODUCTS_TABLE);
        }
    }

}
