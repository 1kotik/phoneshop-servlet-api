package com.es.phoneshop.model.services;

import com.es.phoneshop.model.model.Cart;
import com.es.phoneshop.model.model.CartItem;

import java.util.Optional;

public interface CartService {
    void addItem(Cart cart, Long productId, int quantity);
    Optional<CartItem> getItemByProductId(Cart cart, Long productId);
    void updateItem(Cart cart, Long productId, int quantity);
    void deleteItem(Cart cart, Long productId);
    void clearCart(Cart cart);
}
