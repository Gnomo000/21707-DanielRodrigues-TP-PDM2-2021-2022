package com.example.woods.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Orders {
    @PrimaryKey
    private int id;
    private int woods_id;
    private int users_id;
    private String street;
    private int quantity;
    private String size;
    private String status;

    public Orders(int id, int woods_id, int users_id, String street, int quantity, String size, String status) {
        this.id = id;
        this.woods_id = woods_id;
        this.users_id = users_id;
        this.street = street;
        this.quantity = quantity;
        this.size = size;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWoods_id() {
        return woods_id;
    }

    public void setWoods_id(int woods_id) {
        this.woods_id = woods_id;
    }

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
