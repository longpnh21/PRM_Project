package com.example.projectrestaurant.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.projectrestaurant.daos.AccountDAO;
import com.example.projectrestaurant.daos.ItemDAO;
import com.example.projectrestaurant.dtos.Account;
import com.example.projectrestaurant.dtos.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantMenuActivityViewModel extends AndroidViewModel {
    private ItemDAO dao;

    public RestaurantMenuActivityViewModel(Application application) {
        super(application);
        dao = new ItemDAO();
    }

    public List<Item> getMenu() {
        List<Item> menu = new ArrayList<>();
        try {
            menu = dao.getAllItemsList();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return menu;
    }
}
