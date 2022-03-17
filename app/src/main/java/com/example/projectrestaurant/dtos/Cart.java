package com.example.projectrestaurant.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private List<Item> itemsInCart;
    private String ownerUsername;

    public Cart(String ownerUsername) {
        this.ownerUsername = ownerUsername;
        itemsInCart = new ArrayList<>();
    }

    public List<Item> getItemsInCart() {
        return itemsInCart;
    }

    public void addOrUpdateItem(Item item) {
        if(itemsInCart == null) {
            itemsInCart = new ArrayList<>();
        }
        if(itemsInCart.contains(item)) {
            int index = itemsInCart.indexOf(item);
            itemsInCart.remove(index);
            itemsInCart.add(index, item);
            return;
        }
        itemsInCart.add(item);
    }

    public void removeItem(Item item) {
        if(itemsInCart == null) {
            return;
        }
        int index = itemsInCart.indexOf(item);
        if(index < 0) {
            return;
        }
        itemsInCart.remove(index);
    }

    public int getTotalItems() {
        return itemsInCart.size();
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }
}
