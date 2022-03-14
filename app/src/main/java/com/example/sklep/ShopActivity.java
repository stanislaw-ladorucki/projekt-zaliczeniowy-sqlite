package com.example.sklep;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShopActivity extends BaseActivity {

    private ShopOrder order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        order = new ShopOrder();

        MyDatabase.DatabaseHelper hp = new MyDatabase.DatabaseHelper(this);
        SQLiteDatabase shopDatabase = hp.getWritableDatabase();
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

        ProductsAdapter productsAdapter = new ProductsAdapter(this, products);

        ListView listView = findViewById(R.id.products_list_view);
        listView.setAdapter(productsAdapter);
    }

    public void addToCart(Product product) {
        order.addToOrder(product);

        ((TextView)findViewById(R.id.order_total_price)).setText("Koszt zamówienia: " + order.getTotalPrice());
    }

    public class ProductsAdapter extends ArrayAdapter<Product> {


        private static final int PRODUCT_ID = 699;

        public ProductsAdapter(@NonNull Context context, @NonNull Product[] items) {
            super(context, R.layout.products_catalogue_item, items);

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            if(listItem == null)
                listItem = LayoutInflater.from(getContext()).inflate(R.layout.products_catalogue_item, parent,false);

            Product product = getItem(position);
            TextView productName = listItem.findViewById(R.id.product_name);
            productName.setText(product.getName());

            TextView productPrice = listItem.findViewById(R.id.product_price);
            productPrice.setText(String.valueOf(product.getPrice()));

            View root = listItem.getRootView();
            root.setTag(product.getId());

            View addToCartBtn = listItem.findViewById(R.id.product_add_to_cart_button);
            addToCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToCart(product);
                }
            });

            return listItem;
        }
    }
}