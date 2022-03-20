package com.example.sklep.records;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.sklep.MyDatabase;

public class ProductRecord {

    public static final String TABLE_NAME = "products";
    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "name TEXT NOT NULL," +
            "price REAL" +
            ")";

    public static void insertDemoRecords(SQLiteDatabase database) {
        insertRecord(database, "Komputer Gamingowy BB05", 4499.99);
        insertRecord(database, "Klawiatura mechaniczna SupaGamer gen-11", 299.99);
        insertRecord(database, "Mysz komputerowa GGEZ JP00", 99.99);
        insertRecord(database, "Słuchawki + Mikrofon AudioMeta vCC", 249.99);
    }

    private final int id;
    private final String name;
    private final double price;

    public ProductRecord(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public static ProductRecord[] fetchProducts(Context context, @Nullable String whereClause) {
        MyDatabase.DatabaseHelper helper = new MyDatabase.DatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor query = database.query(TABLE_NAME, null, whereClause, null, null, null, null);
        ProductRecord[] productRecords = new ProductRecord[query.getCount()];
        int index = 0;
        while (query.moveToNext()) {
            int id = query.getInt(query.getColumnIndexOrThrow("id"));
            String name = query.getString(query.getColumnIndexOrThrow("name"));
            double price = query.getDouble(query.getColumnIndexOrThrow("price"));

            productRecords[index] = new ProductRecord(id, name, price);
            index++;
        }
        query.close();

        return productRecords;
    }

    public static @Nullable
    ProductRecord getRecordById(Context context, int id) {
        ProductRecord[] p = fetchProducts(context, String.format("id = %d", id));
        if (p.length <= 0)
            return null;
        else
            return p[0];
    }

    public static ProductRecord insertRecord(Context context, String productName, double productPrice) {
        MyDatabase.DatabaseHelper helper = new MyDatabase.DatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues product = new ContentValues();
        product.put("name", productName);
        product.put("price", productPrice);
        long id = database.insert("products", null, product);

        return new ProductRecord((int) id, productName, productPrice);
    }

    private static ProductRecord insertRecord(SQLiteDatabase database, String productName, double productPrice) {
        ContentValues product = new ContentValues();
        product.put("name", productName);
        product.put("price", productPrice);
        long id = database.insert("products", null, product);
        return new ProductRecord((int) id, productName, productPrice);
    }
}
