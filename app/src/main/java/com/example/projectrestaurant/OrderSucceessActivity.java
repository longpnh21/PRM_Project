package com.example.projectrestaurant;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.projectrestaurant.dtos.Cart;

public class OrderSucceessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_succeess);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Restaurant");
        actionBar.setSubtitle("Lô E2a-7, Đường D1, Đ. D1, Long Thạnh Mỹ, Thành Phố Thủ Đức, Thành phố Hồ Chí Minh");
        actionBar.setDisplayHomeAsUpEnabled(false);


        TextView buttonDone = findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getIntent().getStringExtra("username");
                Intent switchToRestaurantMenuActivity = new Intent(OrderSucceessActivity.this, RestaurantMenuActivity.class);
                switchToRestaurantMenuActivity.putExtra("username", username);
                switchToRestaurantMenuActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(switchToRestaurantMenuActivity, 0);
                finish();
            }
        });
    }

}