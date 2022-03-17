package com.example.projectrestaurant.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.projectrestaurant.daos.AccountDAO;
import com.example.projectrestaurant.dtos.Account;

import java.sql.SQLException;

public class MainActivityViewModel extends AndroidViewModel {
    private AccountDAO dao;

    public MainActivityViewModel(Application application) {
        super(application);
        dao = new AccountDAO();
    }

    public Account Login(String email, String password) {
        try {
            return dao.login(email, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
