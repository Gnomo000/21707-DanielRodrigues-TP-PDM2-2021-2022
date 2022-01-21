package com.example.woods.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Woods {
    @PrimaryKey
    private int id;
    private String wood_type;
    private String color;
    private String size;
    private int quantity;

    public Woods(int id, String wood_type, String color, String size, int quantity) {
        this.id = id;
        this.wood_type = wood_type;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWood_type() {
        return wood_type;
    }

    public void setWood_type(String wood_type) {
        this.wood_type = wood_type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
