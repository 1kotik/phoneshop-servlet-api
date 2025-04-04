package com.es.phoneshop.model.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart extends GenericEntity implements Serializable, Cloneable {
    protected List<CartItem> items;
    protected int totalQuantity;
    protected BigDecimal totalPrice;

    public Cart() {
        items = new ArrayList<>();
    }

    public Cart(List<CartItem> items) {
        this.items = new ArrayList<>(items);
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
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


    @Override
    public Cart clone() {
        Cart clone;
        clone = (Cart) super.clone();
        clone.items = this.items.stream()
                .map(CartItem::clone).toList();
        return clone;
    }
}
