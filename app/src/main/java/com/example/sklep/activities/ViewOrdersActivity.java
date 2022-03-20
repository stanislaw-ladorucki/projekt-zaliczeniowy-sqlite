package com.example.sklep.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sklep.R;
import com.example.sklep.records.ProductRecord;
import com.example.sklep.records.ShopOrderRecord;

import java.text.SimpleDateFormat;

public class ViewOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);


        updateOrdersList();
    }

    public void updateOrdersList() {
        ListView listView = findViewById(R.id.orders_list_view);

        ShopOrderRecord[] orders = ShopOrderRecord.fetchOrders(this, null);

        OrdersAdapter adapter = new OrdersAdapter(this, orders);
        listView.setAdapter(adapter);
    }

    public static class OrdersAdapter extends ArrayAdapter<ShopOrderRecord> {

        public OrdersAdapter(@NonNull Context context, @NonNull ShopOrderRecord[] items) {
            super(context, R.layout.products_catalogue_item, items);

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            if(listItem == null)
                listItem = LayoutInflater.from(getContext()).inflate(R.layout.shop_order_item, parent,false);

            ShopOrderRecord orderRecord = getItem(position);
            TextView orderedBy = listItem.findViewById(R.id.ordered_by);
            orderedBy.setText(orderRecord.getCustomerName());

            TextView totalPrice = listItem.findViewById(R.id.order_total_price);
            totalPrice.setText(String.format("TOTAL: %.2f PLN", orderRecord.getTotalPrice()));

            TextView orderDate = listItem.findViewById(R.id.order_date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy HH:mm");
            orderDate.setText(dateFormat.format(orderRecord.getDate()));

            TextView orderDetials = listItem.findViewById(R.id.order_details);
            StringBuilder details = new StringBuilder();
            for (ProductRecord p : orderRecord.getProducts()) {
                details.append(p.getName()).append("\n");
            }
            orderDetials.setText(details);

            View root = listItem.getRootView();
            root.setTag(orderRecord.getId());

            return listItem;
        }
    }
}