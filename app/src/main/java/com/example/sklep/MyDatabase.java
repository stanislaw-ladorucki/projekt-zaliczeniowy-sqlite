package com.example.sklep;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sklep.records.ProductRecord;
import com.example.sklep.records.ShopOrderProductRecord;
import com.example.sklep.records.ShopOrderRecord;


public class MyDatabase {
    public static class DatabaseHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 6;
        public static final String DATABASE_NAME = "sklep.db";
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(ProductRecord.SQL_CREATE_TABLE);
            db.execSQL(ShopOrderRecord.SQL_CREATE_TABLE);
            db.execSQL(ShopOrderProductRecord.SQL_CREATE_TABLE);

            ProductRecord.insertDemoRecords(db);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS " + ProductRecord.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + ShopOrderRecord.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + ShopOrderProductRecord.TABLE_NAME);
            db.execSQL(ProductRecord.SQL_CREATE_TABLE);
            db.execSQL(ShopOrderRecord.SQL_CREATE_TABLE);
            db.execSQL(ShopOrderProductRecord.SQL_CREATE_TABLE);

            ProductRecord.insertDemoRecords(db);
        }
    }

}
