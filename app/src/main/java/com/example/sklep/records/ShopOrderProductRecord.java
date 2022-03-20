package com.example.sklep.records;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.Date;

public class ShopOrderProductRecord {
    public static final String TABLE_NAME = "orderProduct";
    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "order_id INTEGER NOT NULL," +
            "product_id INTEGER NOT NULL" +
            ")";
}
