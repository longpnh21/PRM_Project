package com.example.projectrestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projectrestaurant.dtos.Account;
import com.example.projectrestaurant.dtos.Role;
import com.example.projectrestaurant.utils.EmailValidation;
import com.example.projectrestaurant.viewmodels.MainActivityViewModel;
import com.example.projectrestaurant.viewmodels.ShowCategoryListActivityViewModel;

public class MainActivity extends AppCompatActivity {

    TextView errorLogin;
    EditText editEmail, editPassword;
    MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        errorLogin = findViewById(R.id.errorLogin);
    }

    public void clickToLogin(View view) {
        try {
            boolean isValid = true;
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();
            String error = "";
            if(email == null || email.equals("")) {
                error += "Username/Email is required\n";
                isValid = false;
            }
            else if(email.contains("@") && !EmailValidation.isValidEmail(email)) {
                error += "Email is invalid\n";
                isValid = false;
            }
            if(password == null || password.equals("")) {
                error += "Password is required";
                isValid = false;
            }
            if(!isValid) {
                errorLogin.setVisibility(View.VISIBLE);
                errorLogin.setBackgroundColor(Color.RED);
                errorLogin.setText(error);
                return;
            }
            errorLogin.setVisibility(View.INVISIBLE);

            Account user = viewModel.Login(email, password);
            if(user == null) throw new Exception("Incorrect Email or Password");
            Intent intent;
            switch (user.getRole()) {
                case Guest:
                    intent = new Intent(MainActivity.this, RestaurantMenuActivity.class);
                    break;
                case Staff:
                    intent = new Intent(MainActivity.this, ShowCategoryListActivity.class);
                    break;
                default:
                    throw new Exception("Cannot authorize");
            }

            intent.putExtra("username", user.getUsername());
            startActivity(intent);
            finish();

        }
        catch (Exception e) {
            errorLogin.setVisibility(View.VISIBLE);
            errorLogin.setText(e.getMessage());
            errorLogin.setBackgroundColor(Color.RED);
        }
    }

    public void clickToLoginAsGuest(View view) {
    }
}