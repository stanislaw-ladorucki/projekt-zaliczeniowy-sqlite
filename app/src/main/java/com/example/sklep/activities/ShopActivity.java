package com.example.sklep.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sklep.GlobalUserSessionManager;
import com.example.sklep.R;
import com.example.sklep.ShopOrder;
import com.example.sklep.records.ProductRecord;

public class ShopActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);


        ProductRecord[] productRecords = ProductRecord.fetchProducts(this, null);

        ProductsAdapter productsAdapter = new ProductsAdapter(this, productRecords);

        ListView listView = findViewById(R.id.products_list_view);
        listView.setAdapter(productsAdapter);

        updateOrderTotalCounter();
    }

    @Override
    protected void onStart() {
        super.onStart();

        ProductRecord[] productRecords = ProductRecord.fetchProducts(this, null);

        ProductsAdapter productsAdapter = new ProductsAdapter(this, productRecords);

        ListView listView = findViewById(R.id.products_list_view);
        listView.setAdapter(productsAdapter);

        updateOrderTotalCounter();
    }

    public void updateOrderTotalCounter() {
        ShopOrder order = GlobalUserSessionManager.getCartOrder();
        ((TextView)findViewById(R.id.order_total_price)).setText(String.format("TOTAL: %.2f PLN", order.getTotalPrice()));
    }

    public void addToCart(ProductRecord productRecord) {
        ShopOrder order = GlobalUserSessionManager.getCartOrder();
        order.addToOrder(productRecord);

        updateOrderTotalCounter();

        Toast.makeText(this, "Product added to cart", Toast.LENGTH_SHORT).show();
    }

    public void goToCart(View view) {
        Intent intent = new Intent(this, MyCartActivity.class);
        startActivity(intent);
    }

    public class ProductsAdapter extends ArrayAdapter<ProductRecord> {

        public ProductsAdapter(@NonNull Context context, @NonNull ProductRecord[] items) {
            super(context, R.layout.products_catalogue_item, items);

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            if(listItem == null)
                listItem = LayoutInflater.from(getContext()).inflate(R.layout.products_catalogue_item, parent,false);

            ProductRecord productRecord = getItem(position);
            TextView productName = listItem.findViewById(R.id.product_name);
            productName.setText(productRecord.getName());

            TextView productPrice = listItem.findViewById(R.id.product_price);
            productPrice.setText(String.format("%.2f PLN", productRecord.getPrice()));

            View root = listItem.getRootView();
            root.setTag(productRecord.getId());

            View addToCartBtn = listItem.findViewById(R.id.product_add_to_cart_button);
            addToCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToCart(productRecord);
                }
            });

            return listItem;
        }
    }
}