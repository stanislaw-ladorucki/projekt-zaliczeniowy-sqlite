package com.example.sklep.activities;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sklep.R;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_activity_manage_products:
                intent = new Intent(this, ProductsManagerActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_activity_shop:
                intent = new Intent(this, ShopActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_activity_cart:
                intent = new Intent(this, MyCartActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_activity_orders:
                intent = new Intent(this, ViewOrdersActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_activity_about_me:
                intent = new Intent(this, AboutMeActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_user_data_activity:
                intent = new Intent(this, UserDataActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
