package com.es.phoneshop.model.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public Cart(List<CartItem> items) {
        this.items = new ArrayList<>(items);
    }

    public List<CartItem> getItems() {
        return items;
    }

}
