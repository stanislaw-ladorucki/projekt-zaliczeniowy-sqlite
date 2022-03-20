package com.example.sklep.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sklep.MyDatabase;
import com.example.sklep.R;
import com.example.sklep.records.ProductRecord;

public class ProductsManagerActivity extends BaseActivity {

    public SQLiteDatabase shopDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_manager);

        MyDatabase.DatabaseHelper hp = new MyDatabase.DatabaseHelper(this);
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
        ProductRecord[] productRecords = ProductRecord.fetchProducts(this, null);

        ProductsAdapter productsAdapter =
                new ProductsAdapter(this, productRecords);

        ListView listView = findViewById(R.id.products_list_view);
        listView.setAdapter(productsAdapter);
    }

    public void resetProducts(View view) {
        shopDatabase.delete("products", null, null);
        updateProductsListView();
    }

    public class ProductsAdapter extends ArrayAdapter<ProductRecord> {

        public ProductsAdapter(@NonNull Context context, @NonNull ProductRecord[] items) {
            super(context, R.layout.products_manager_item, items);

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            if(listItem == null)
                listItem = LayoutInflater.from(getContext()).inflate(R.layout.products_manager_item, parent,false);

            ProductRecord productRecord = getItem(position);
            TextView productName = listItem.findViewById(R.id.product_name);
            productName.setText(productRecord.getName());

            TextView productPrice = listItem.findViewById(R.id.product_price);
            productPrice.setText(String.valueOf(productRecord.getPrice()));

            View root = listItem.getRootView();
            root.setTag(productRecord.getId());

            View remove = listItem.findViewById(R.id.product_remove_button);

            MyDatabase.DatabaseHelper helper = new MyDatabase.DatabaseHelper(this.getContext());
            SQLiteDatabase database = helper.getWritableDatabase();

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View v = (View) view.getParent();
                    int product_id = (int) v.getTag();

                    database.delete("products", "id = " + product_id, null);

                    updateProductsListView();
                }
            });
            return listItem;
        }
    }
}