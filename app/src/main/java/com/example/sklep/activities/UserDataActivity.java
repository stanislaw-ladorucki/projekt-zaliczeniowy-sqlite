package com.example.sklep.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sklep.GlobalUserSessionManager;
import com.example.sklep.R;

public class UserDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        EditText input = findViewById(R.id.user_name_input);
        input.setText(GlobalUserSessionManager.getUserCustomerName());
    }


    public void saveChanges(View view) {
        EditText input = findViewById(R.id.user_name_input);
        GlobalUserSessionManager.setUserName(input.getText().toString());

        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }
}