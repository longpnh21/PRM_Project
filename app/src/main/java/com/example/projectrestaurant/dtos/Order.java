package com.example.projectrestaurant.dtos;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private int id;
    private String username;
    private Date orderDate;

    public Order(int id, String username, Date orderDate) {
        this.id = id;
        this.username = username;
        this.orderDate = orderDate;
    }

    public Order(String username, Date orderDate) {
        this.username = username;
        this.orderDate = orderDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
