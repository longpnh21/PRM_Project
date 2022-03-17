package com.example.projectrestaurant.dtos;

import java.io.Serializable;

public class Account implements Serializable {
    private String username;
    private String password;
    private Role role;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = Role.Guest;
    }

    public Account(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Account(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = Role.valueOf(role);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
