package com.example.projectrestaurant.dtos;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private String name;
    private float price;
    private int categoryId;
    private int quantity;

    public Item() {
    }

    public Item(String name, float price, int categoryId, int quantity) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.quantity = quantity;
    }

    public Item(int id, String name, float price, int categoryId, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Id: " + id + "\nName: " + name + "\nPrice: " + price + "\ncategoryId: " + categoryId + "\nquantity: " + quantity;
    }
}
