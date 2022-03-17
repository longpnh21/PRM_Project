package com.example.projectrestaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectrestaurant.adapters.MenuListAdapter;
import com.example.projectrestaurant.dtos.Cart;
import com.example.projectrestaurant.dtos.Item;
import com.example.projectrestaurant.viewmodels.RestaurantMenuActivityViewModel;

import java.util.List;

public class RestaurantMenuActivity extends AppCompatActivity implements MenuListAdapter.MenuListClickListener {
    private List<Item> menu = null;
    private MenuListAdapter menuListAdapter;
    private List<Item> itemsInCartList;
    private Cart cart;
    private int totalItemInCart = 0;
    private TextView buttonCheckout;
    private RestaurantMenuActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Restaurant");
        actionBar.setSubtitle("Lô E2a-7, Đường D1, Đ. D1, Long Thạnh Mỹ, Thành Phố Thủ Đức, Thành phố Hồ Chí Minh");
        actionBar.setDisplayHomeAsUpEnabled(true);
        String username = getIntent().getStringExtra("username");
        cart = new Cart(username);
        cart.setOwnerUsername(getIntent().getStringExtra("username"));

        viewModel = new ViewModelProvider(this).get(RestaurantMenuActivityViewModel.class);
        menu = viewModel.getMenu();
        initRecyclerView();


        buttonCheckout = findViewById(R.id.buttonCheckout);
        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart != null && cart.getTotalItems() <= 0) {
                    Toast.makeText(RestaurantMenuActivity.this, "Please add some items in cart.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(RestaurantMenuActivity.this, PlaceYourOrderActivity.class);
                intent.putExtra("Cart", cart);
                startActivityForResult(intent, 1000);
                finish();
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        menuListAdapter = new MenuListAdapter(menu, this);
        recyclerView.setAdapter(menuListAdapter);
    }

    @Override
    public void onAddToCartClick(Item item) {
        cart.addOrUpdateItem(item);
        buttonCheckout.setText("Checkout (" +cart.getTotalItems() +") items");
    }

    @Override
    public void onUpdateCartClick(Item item) {
        cart.addOrUpdateItem(item);
        buttonCheckout.setText("Checkout (" + cart.getTotalItems() +") items");
    }

    @Override
    public void onRemoveFromCartClick(Item item) {
        cart.removeItem(item);
        buttonCheckout.setText("Checkout (" + cart.getTotalItems() +") items");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            //
            finish();
        }
    }
}