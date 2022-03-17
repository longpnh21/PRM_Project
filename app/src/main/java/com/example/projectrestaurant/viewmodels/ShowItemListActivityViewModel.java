package com.example.projectrestaurant.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.projectrestaurant.daos.ItemDAO;
import com.example.projectrestaurant.dtos.Item;

import java.util.List;

public class ShowItemListActivityViewModel extends AndroidViewModel {

    private MutableLiveData<List<Item>> listOfItems;
    private ItemDAO dao;

    public ShowItemListActivityViewModel(Application application) {
        super(application);
        listOfItems = new MutableLiveData<>();

        dao = new ItemDAO();
    }

    public MutableLiveData<List<Item>>  getItemsListObserver() {
        return listOfItems;
    }

    public void getAllItemsList(int categoryID) {
        try {
            List<Item> itemsList=  dao.getAllItemsList(categoryID);
            if(itemsList.size() > 0)
            {
                listOfItems.postValue(itemsList);
            }else {
                listOfItems.postValue(null);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertItem(Item item) {
        try {
            dao.insertItem(item);
            getAllItemsList(item.getCategoryId());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateItem(Item item) {
        try {
            dao.updateItem(item);
            getAllItemsList(item.getCategoryId());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(Item item) {
        try {
            dao.deleteItem(item);
            getAllItemsList(item.getCategoryId());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
