package com.example.etel4yourdoor;

import java.io.Serializable;

public class FoodItem implements Serializable {
    private String name;
    private int price;
    private int quantity;

    public FoodItem(String name, int price) {
        this.name = name;
        this.price = price;
        this.quantity = 1;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity() {
        this.quantity++;
    }

    public void decreaseQuantity() {
        if (this.quantity > 1) {
            this.quantity--;
        }
    }

    public int getTotalPrice() {
        return price * quantity;
    }
}