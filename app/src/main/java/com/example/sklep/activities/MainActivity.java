package com.example.sklep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sklep.R;
import com.example.sklep.records.ProductRecord;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToShop(View view) {
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }
}