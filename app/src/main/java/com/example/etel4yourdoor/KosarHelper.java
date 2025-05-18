package com.example.etel4yourdoor;

import java.util.ArrayList;
import java.util.List;

public class KosarHelper {
    private static final KosarHelper instance = new KosarHelper();
    private final List<FoodItem> cart = new ArrayList<>();

    private KosarHelper() {}

    public static KosarHelper getInstance() {
        return instance;
    }

    public void addToCart(FoodItem item) {
        for (FoodItem cartItem : cart) {
            if (cartItem.getName().equals(item.getName())) {
                cartItem.increaseQuantity();
                return;
            }
        }
        cart.add(item);
    }

    public List<FoodItem> getCart() {
        return cart;
    }

    public void removeFromCart(FoodItem item) {
        cart.remove(item);
    }

}
