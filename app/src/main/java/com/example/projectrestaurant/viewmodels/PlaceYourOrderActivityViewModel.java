package com.example.projectrestaurant.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.projectrestaurant.daos.ItemDAO;
import com.example.projectrestaurant.daos.OrderDAO;
import com.example.projectrestaurant.daos.OrderDetailDAO;
import com.example.projectrestaurant.dtos.Cart;
import com.example.projectrestaurant.dtos.Item;
import com.example.projectrestaurant.dtos.Order;
import com.example.projectrestaurant.dtos.OrderDetail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlaceYourOrderActivityViewModel extends AndroidViewModel {
    private ItemDAO itemDAO;
    private OrderDAO orderDAO;
    private OrderDetailDAO orderDetailDAO;

    public PlaceYourOrderActivityViewModel(@NonNull Application application) {
        super(application);
        itemDAO = new ItemDAO();
        orderDAO = new OrderDAO();
        orderDetailDAO = new OrderDetailDAO();
    }

    public boolean placeOrder(Cart cart) throws Exception {
        boolean isSuccess = false;
        int updatedItem = 0;
        boolean checkAvailableItem = false;

        for(int i = 0; i < cart.getTotalItems(); i++) {
            checkAvailableItem = checkItem(cart.getItemsInCart().get(i));
            if(!checkAvailableItem) {
                for(int j = 0; j < updatedItem; j++) {
                    returnItem(cart.getItemsInCart().get(i));
                }
                return false;
            }
            updatedItem++;
        }

        if(checkAvailableItem) {
            createOrder(cart);
            isSuccess = true;
        }

        return isSuccess;
    }

    public void createOrder(Cart cart) throws Exception {
        Date orderDate = new Date();
        boolean successOrder = orderDAO.insertOrder(new Order(cart.getOwnerUsername(), orderDate));
        if (successOrder) {
            Order insertedOrder = orderDAO.getOrderByUsernameAndDate(cart.getOwnerUsername(), orderDate);
            if(insertedOrder != null) {
                createOrderDetail(insertedOrder, cart);
                return;
            }
            else throw new Exception("cannot find order");
        }
        else throw new Exception("cannot create order");
    }

    private void createOrderDetail(Order insertedOrder, Cart cart) throws Exception {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for(int i = 0; i < cart.getTotalItems(); i++) {
            Item item = cart.getItemsInCart().get(i);
            orderDetailList.add(new OrderDetail(insertedOrder.getId(), item.getId(), item.getPrice(), item.getQuantity()));
        }
        orderDetailDAO.insertOrderDetail(orderDetailList);
    }

    private boolean checkItem(Item item) throws Exception {
        Item inStock = itemDAO.getItemById(item.getId());
        if(inStock.getQuantity() > item.getQuantity()) {
            inStock.setQuantity(inStock.getQuantity() - item.getQuantity());
            return itemDAO.updateItem(inStock);
        }
        return false;
    }

    private boolean returnItem(Item item) throws Exception {
        Item inStock = itemDAO.getItemById(item.getId());
        inStock.setQuantity(inStock.getQuantity() + item.getQuantity());
        return itemDAO.updateItem(inStock);
    }

}