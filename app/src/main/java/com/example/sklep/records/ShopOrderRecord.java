package com.example.sklep.records;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.sklep.MyDatabase;

import java.util.Date;

public class ShopOrderRecord {
    public static final String TABLE_NAME = "shopOrder";
    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "customer_name TEXT NOT NULL," +
            "total_price REAL," +
            "date INTEGER" +
            ")";

    private final int id;
    private final String customer_name;
    private final double total_price;
    private final long date;

    private final ProductRecord[] products;

    public ShopOrderRecord(int id, String customer_name, double total_price, long date, ProductRecord[] products) {
        this.id = id;
        this.customer_name = customer_name;
        this.total_price = total_price;
        this.date = date;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customer_name;
    }

    public double getTotalPrice() {
        return total_price;
    }

    public ProductRecord[] getProducts() {
        return products;
    }

    public Date getDate() {
        return new Date(date * 1000);
    }

    public static ShopOrderRecord[] fetchOrders(Context context, @Nullable String whereClause) {
        MyDatabase.DatabaseHelper helper = new MyDatabase.DatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor query = database.query(TABLE_NAME, null, whereClause, null, null, null, null);
        ShopOrderRecord[] orders = new ShopOrderRecord[query.getCount()];
        int index = 0;
        while (query.moveToNext()) {
            int id = query.getInt(query.getColumnIndexOrThrow("id"));

            Cursor productsQuery = database.rawQuery(
                    "SELECT product.name, product.price, product.id" +
                            " FROM " + ShopOrderProductRecord.TABLE_NAME +
                            " JOIN " + ProductRecord.TABLE_NAME + " AS product ON product.id = product_id" +
                            " WHERE order_id = " + id, null);
            ProductRecord[] products = new ProductRecord[productsQuery.getCount()];
            int indexx = 0;
            while (productsQuery.moveToNext()) {
                int product_id = productsQuery.getInt(productsQuery.getColumnIndexOrThrow("id"));
                String name = productsQuery.getString(productsQuery.getColumnIndexOrThrow("name"));
                double price = productsQuery.getDouble(productsQuery.getColumnIndexOrThrow("price"));

                products[indexx] = new ProductRecord(product_id, name, price);
                indexx++;
            }
            productsQuery.close();

            String customer_name = query.getString(query.getColumnIndexOrThrow("customer_name"));
            double total_price = query.getDouble(query.getColumnIndexOrThrow("total_price"));
            long date = query.getLong(query.getColumnIndexOrThrow("date"));

            orders[index] = new ShopOrderRecord(id, customer_name, total_price, date, products);
            index++;
        }
        query.close();

        return orders;
    }

    public static @Nullable ShopOrderRecord insertRecord(Context context, String customer_name, double total_price, long date, ProductRecord[] products) {
        MyDatabase.DatabaseHelper helper = new MyDatabase.DatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues order = new ContentValues();
        order.put("customer_name", customer_name);
        order.put("total_price", total_price);
        order.put("date", date);
        long order_id = database.insert("shopOrder", null, order);

        if (order_id >= 0) {

            for (ProductRecord p : products) {
                ContentValues orderProduct = new ContentValues();
                orderProduct.put("order_id", order_id);
                orderProduct.put("product_id", p.getId());
            }

            return new ShopOrderRecord((int) order_id, customer_name, total_price, date, products);
        } else {
            return null;
        }
    }
}
