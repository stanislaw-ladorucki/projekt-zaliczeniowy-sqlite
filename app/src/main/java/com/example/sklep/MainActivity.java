package com.example.sklep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private MyDatabase.DatabaseHelper hp;
    public SQLiteDatabase shopDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hp = new MyDatabase.DatabaseHelper(this);
        shopDatabase = hp.getWritableDatabase();

        updateProductsListView();
    }


    public void createProduct(View view) {
        EditText productNameInput  = findViewById(R.id.product_name_input);
        EditText productPriceInput = findViewById(R.id.product_price_input);

        String productName = productNameInput.getText().toString();
        double productPrice;
        try {
            productPrice = Double.parseDouble(productPriceInput.getText().toString());
        } catch (NumberFormatException e){
            productPrice = 0.0;
        }

        if (productName.length() > 0) {
            ContentValues product = new ContentValues();
            product.put("name", productName);
            product.put("price", productPrice);
            shopDatabase.insert("products", null, product);

            this.updateProductsListView();
        }
    }

    public void updateProductsListView() {
        Cursor query = shopDatabase.query("products", new String[]{"id", "name", "price"}, null, null, null, null, null);
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

        ProductsAdapter productsAdapter =
                new ProductsAdapter(this, products);

        ListView listView = findViewById(R.id.products_list_view);
        listView.setAdapter(productsAdapter);


    }

    public void resetProducts(View view) {
        shopDatabase.delete("products", null, null);
        updateProductsListView();
    }
}