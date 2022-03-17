package com.example.projectrestaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projectrestaurant.adapters.PlaceYourOrderAdapter;
import com.example.projectrestaurant.dtos.Cart;
import com.example.projectrestaurant.dtos.Category;
import com.example.projectrestaurant.dtos.Item;
import com.example.projectrestaurant.viewmodels.PlaceYourOrderActivityViewModel;
import com.example.projectrestaurant.viewmodels.ShowCategoryListActivityViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PlaceYourOrderActivity extends AppCompatActivity {

    private EditText inputName, inputAddress, inputCity, inputState, inputZip,inputCardNumber, inputCardExpiry, inputCardPin ;
    private RecyclerView cartItemsRecyclerView;
    private TextView tvSubtotalAmount, tvDeliveryChargeAmount, tvDeliveryCharge, tvTotalAmount, buttonPlaceYourOrder;
    private SwitchCompat switchDelivery;
    private boolean isDeliveryOn;
    private PlaceYourOrderAdapter placeYourOrderAdapter;
    private PlaceYourOrderActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_your_order);

        Cart cart = (Cart) getIntent().getSerializableExtra("Cart");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Restaurant");
        actionBar.setSubtitle("Lô E2a-7, Đường D1, Đ. D1, Long Thạnh Mỹ, Thành Phố Thủ Đức, Thành phố Hồ Chí Minh");
        actionBar.setDisplayHomeAsUpEnabled(true);
        initViewModel();

        inputName = findViewById(R.id.inputName);
        inputAddress = findViewById(R.id.inputAddress);
        inputCity = findViewById(R.id.inputCity);
        inputState = findViewById(R.id.inputState);
        inputZip = findViewById(R.id.inputZip);
        inputCardNumber = findViewById(R.id.inputCardNumber);
        inputCardExpiry = findViewById(R.id.inputCardExpiry);
        inputCardPin = findViewById(R.id.inputCardPin);
        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount);
        tvDeliveryChargeAmount = findViewById(R.id.tvDeliveryChargeAmount);
        tvDeliveryCharge = findViewById(R.id.tvDeliveryCharge);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder);
        switchDelivery = findViewById(R.id.switchDelivery);

        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView);

        buttonPlaceYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaceOrderButtonClick(cart);
            }
        });

        switchDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    inputAddress.setVisibility(View.VISIBLE);
                    inputCity.setVisibility(View.VISIBLE);
                    inputState.setVisibility(View.VISIBLE);
                    inputZip.setVisibility(View.VISIBLE);
                    tvDeliveryChargeAmount.setVisibility(View.VISIBLE);
                    tvDeliveryCharge.setVisibility(View.VISIBLE);
                    isDeliveryOn = true;
                    calculateTotalAmount(cart);
                } else {
                    inputAddress.setVisibility(View.GONE);
                    inputCity.setVisibility(View.GONE);
                    inputState.setVisibility(View.GONE);
                    inputZip.setVisibility(View.GONE);
                    tvDeliveryChargeAmount.setVisibility(View.GONE);
                    tvDeliveryCharge.setVisibility(View.GONE);
                    isDeliveryOn = false;
                    calculateTotalAmount(cart);
                }
            }
        });
        initRecyclerView(cart);
        calculateTotalAmount(cart);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(PlaceYourOrderActivityViewModel.class);
    }

    private void calculateTotalAmount(Cart cart) {
        float subTotalAmount = 0f;

        for(Item item : cart.getItemsInCart()) {
            subTotalAmount += item.getPrice() * item.getQuantity();
        }

        tvSubtotalAmount.setText("$"+String.format("%.2f", subTotalAmount));
        if(isDeliveryOn) {
            tvDeliveryChargeAmount.setText("$"+String.format("%.2f", 30000));
            subTotalAmount += 30000;
        }
        tvTotalAmount.setText("$"+String.format("%.2f", subTotalAmount));
    }

    private void onPlaceOrderButtonClick(Cart cart) {
        try {
            if(TextUtils.isEmpty(inputName.getText().toString())) {
                inputName.setError("Please enter name ");
                return;
            } else if(isDeliveryOn && TextUtils.isEmpty(inputAddress.getText().toString())) {
                inputAddress.setError("Please enter address ");
                return;
            }else if(isDeliveryOn && TextUtils.isEmpty(inputCity.getText().toString())) {
                inputCity.setError("Please enter city ");
                return;
            }else if(isDeliveryOn && TextUtils.isEmpty(inputState.getText().toString())) {
                inputState.setError("Please enter zip ");
                return;
            }else if( TextUtils.isEmpty(inputCardNumber.getText().toString())) {
                inputCardNumber.setError("Please enter card number ");
                return;
            }else if(TextUtils.isEmpty(inputCardExpiry.getText().toString())) {
                inputCardExpiry.setError("Please enter card expiry ");
                return;
            }
            else if( TextUtils.isEmpty(inputCardPin.getText().toString())) {
                inputCardPin.setError("Please enter card pin/cvv ");
                return;
            }
            Date date = new SimpleDateFormat("MMyyyy").parse(inputCardExpiry.getText().toString());
            if(date.before(new Date())) {
                inputCardExpiry.setError("Invalid card expiry ");
                return;
            }

            viewModel.placeOrder(cart);
            //start success activity..
            Intent intent = new Intent(PlaceYourOrderActivity.this, OrderSucceessActivity.class);
            intent.putExtra("username", cart.getOwnerUsername());
            startActivityForResult(intent, 1000);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView(Cart cart) {
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeYourOrderAdapter = new PlaceYourOrderAdapter(cart.getItemsInCart());
        cartItemsRecyclerView.setAdapter(placeYourOrderAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1000) {
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
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
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}