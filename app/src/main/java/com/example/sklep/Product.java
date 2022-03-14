package com.example.sklep;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class Product {

    private static final String TABLE_NAME = "products";

    private final int id;
    private final String name;
    private final double price;

    public Product(int id, String name, double price) {
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

    public static Product[] fetchProducts(Context context) {
        MyDatabase.DatabaseHelper helper = new MyDatabase.DatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor query = database.query(TABLE_NAME, null, null, null, null, null, null);
        Product[] products = new Product[query.getCount()];
        int index = 0;
        while (query.moveToNext()) {
            int id = query.getInt(query.getColumnIndexOrThrow("id"));
            String name = query.getString(query.getColumnIndexOrThrow("name"));
            double price = query.getDouble(query.getColumnIndexOrThrow("price"));

            products[index] = new Product(id, name, price);
            index++;
        }
        query.close();

        return products;
    }
}
