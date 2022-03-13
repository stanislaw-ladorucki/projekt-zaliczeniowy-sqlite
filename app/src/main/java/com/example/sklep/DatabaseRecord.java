package com.example.sklep;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class DatabaseRecord {

//    public static getDatabaseName();

    public enum RecordFieldType {
        Text,
        Integer,
        Real,
    };

//    public static <TRecordType extends DatabaseRecord> HashMap<String, Object> fetchRectords(Context context, String tableName) {
//        MyDatabase.DatabaseHelper helper = new MyDatabase.DatabaseHelper(context);
//        SQLiteDatabase database = helper.getWritableDatabase();
//
//        Cursor query = database.query(tableName, null, null, null, null, null, null);
//        HashMap<String, Object>[] records = new HashMap<String, Object>[][query.getCount()];
//        int index = 0;
//        while (query.moveToNext()) {
//            int id = query.getInt(query.getColumnIndexOrThrow("id"));
//            String name = query.getString(query.getColumnIndexOrThrow("name"));
//            double price = query.getDouble(query.getColumnIndexOrThrow("price"));
//
//            records[index] = new HashMap<String, Object>();
//            index++;
//        }
//        query.close();
//
//        return records;
//    }
}

