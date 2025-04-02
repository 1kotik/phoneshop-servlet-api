package com.es.phoneshop.model.services;

import com.es.phoneshop.model.model.Cart;
import com.es.phoneshop.model.model.Order;


public interface OrderService {
    Order getOrderBySecureId(String secureId);
    void updateOrder(Order order, Cart cart);
    void placeOrder(Order order);
}
