package com.example.sklep;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProductsAdapter extends ArrayAdapter<Product> {


    private static final int PRODUCT_ID = 699;

    public ProductsAdapter(@NonNull Context context, @NonNull Product[] items) {
        super(context, R.layout.products_item, items);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.products_item, parent,false);

        Product product = getItem(position);
        TextView productName = listItem.findViewById(R.id.product_name);
        productName.setText(product.getName());

        TextView productPrice = listItem.findViewById(R.id.product_price);
        productPrice.setText(String.valueOf(product.getPrice()));

        View root = listItem.getRootView();
        root.setTag(product.getId());

        View remove = listItem.findViewById(R.id.product_remove_button);

        MainActivity mainActivity = ((MainActivity) this.getContext());
        MyDatabase.DatabaseHelper helper = new MyDatabase.DatabaseHelper(this.getContext());
        SQLiteDatabase database = helper.getWritableDatabase();

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = (View) view.getParent();
                int product_id = (int) v.getTag();

                database.delete("products", "id = " + product_id, null);
                mainActivity.updateProductsListView();

                Log.d("STACHU", String.valueOf(product_id));
            }
        });
        return listItem;
    }


}
