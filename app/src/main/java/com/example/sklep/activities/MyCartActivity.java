package com.example.sklep.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
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
import com.example.sklep.records.ShopOrderRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MyCartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        updateListView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateListView();
    }

    private void updateListView() {
        ListView listView = findViewById(R.id.cart_products_list_view);
        ShopOrder order = GlobalUserSessionManager.getCartOrder();
        ShopOrder.ProductCountsMap orderCounts = order.getProductCounts();

        Set<Integer> entrySet = orderCounts.keySet();

        Integer[] product_ids = new Integer[entrySet.size()];
        entrySet.toArray(product_ids);

        ProductsCounterMapAdapter adapter = new ProductsCounterMapAdapter(this, product_ids);
        listView.setAdapter(adapter);

        ((TextView)findViewById(R.id.order_total_price)).setText(String.format("TOTAL: %.2f PLN", order.getTotalPrice()));

    }


    public void confirmOrder(View view) {
        ShopOrder order = GlobalUserSessionManager.getCartOrder();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {


            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> messages = new ArrayList<String>(){
                {
                    add(String.format(
                        Locale.US,
                        "NOWE ZAMÓWIENIE OD UŻYTKOWNIKA %s\n",
                        GlobalUserSessionManager.getUserCustomerName()));
                    add(String.format(
                        Locale.US,
                        "Całkowita cena zamówienia: %.2f PLN\n",
                        order.getTotalPrice()));
                }
            };
            for (Map.Entry<Integer, Integer> count : order.getProductCounts().entrySet()) {
                ProductRecord p = ProductRecord.getRecordById(this, count.getKey());
                messages.add(String.format("%s x%d cena: %.2f PLN\n", p.getName(), count.getValue(), p.getPrice()));
            }
            smsManager.sendMultipartTextMessage("503961779", null, messages, null, null);

            Toast.makeText(this, "Order sent", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions(new String[] {Manifest.permission.SEND_SMS}, R.id.send_sms_permission_request_code);
        }


        ShopOrderRecord.insertRecord(this,
                GlobalUserSessionManager.getUserCustomerName(),
                order.getTotalPrice(),
                System.currentTimeMillis()/1000,
                order.getProducts()
        );

        GlobalUserSessionManager.resetCartOrder();

        updateListView();
    }

    public class ProductsCounterMapAdapter extends ArrayAdapter<Integer> {


        private static final int PRODUCT_ID = 699;

        public ProductsCounterMapAdapter(@NonNull Context context, @NonNull Integer[] items) {
            super(context, R.layout.product_cart_item, items);

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            if(listItem == null)
                listItem = LayoutInflater.from(getContext()).inflate(R.layout.product_cart_item, parent,false);

            int product_id = getItem(position);

            Set<Map.Entry<Integer, Integer>> productSet = GlobalUserSessionManager.getCartOrder().getProductCounts().entrySet();

            int amount = -1;
            for (Map.Entry<Integer, Integer> p : productSet) {
                if (p.getKey() == product_id) {
                    amount = p.getValue();
                }
            }
            if (amount <= 0) {
                listItem.setVisibility(View.INVISIBLE);
                return listItem;
            }

            ProductRecord productRecord = ProductRecord.getRecordById(getContext(), product_id);
            if (productRecord == null) {
                listItem.setVisibility(View.INVISIBLE);
                return listItem;
            }

            TextView productName = listItem.findViewById(R.id.product_name);
            productName.setText(productRecord.getName());

            TextView productQty = listItem.findViewById(R.id.product_quantity);
            productQty.setText(String.valueOf(amount));

            View root = listItem.getRootView();
            root.setTag(R.id.product_id_tag, product_id);

            View amountPlusBtn = listItem.findViewById(R.id.product_amount_plus_button);
            View amountMinusBtn = listItem.findViewById(R.id.product_amount_minus_button);
            amountMinusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int product_id = (int) root.getTag(R.id.product_id_tag);
                    GlobalUserSessionManager.getCartOrder().removeFromOrderById(product_id);

                    updateListView();
                }
            });

            amountPlusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int product_id = (int) root.getTag(R.id.product_id_tag);
                    ProductRecord productRecord = ProductRecord.getRecordById(getContext(), product_id);
                    if (productRecord !=null) {
                        GlobalUserSessionManager.getCartOrder().addToOrder(productRecord);

                        updateListView();
                    }
                }
            });

            return listItem;
        }
    }
}