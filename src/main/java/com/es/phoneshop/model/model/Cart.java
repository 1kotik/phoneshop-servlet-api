package com.es.phoneshop.model.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private List<CartItem> items;
    private int totalQuantity;
    private BigDecimal totalPrice;

    public Cart() {
        items = new ArrayList<>();
    }

    public Cart(List<CartItem> items) {
        this.items = new ArrayList<>(items);
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }
}
