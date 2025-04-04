package com.es.phoneshop.model.services;

import com.es.phoneshop.model.dao.ArrayListOrderDao;
import com.es.phoneshop.model.dao.OrderDao;
import com.es.phoneshop.model.model.Cart;
import com.es.phoneshop.model.model.CartItem;
import com.es.phoneshop.model.model.Order;

import java.math.BigDecimal;
import java.util.UUID;

public class DefaultOrderService implements OrderService {
    private static DefaultOrderService orderService;
    private OrderDao orderDao;

    public static synchronized DefaultOrderService getInstance() {
        if (orderService == null) {
            orderService = new DefaultOrderService();
        }
        return orderService;
    }

    private DefaultOrderService() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Override
    public void updateOrder(Order order, Cart cart) {
        synchronized (order) {
            order.setItems(cart.getItems().stream()
                    .map(CartItem::clone)
                    .toList());
            order.setSubtotal(cart.getTotalPrice());
            order.setDeliveryCost(calculateDeliveryCost());
            order.setTotalPrice(order.getSubtotal().add(order.getDeliveryCost()));
            order.setTotalQuantity(cart.getTotalQuantity());
        }
    }

    @Override
    public void placeOrder(Order order) {
        order.setSecureId(UUID.randomUUID().toString());
        orderDao.save(order);
    }

    @Override
    public Order getOrderBySecureId(String secureId) {
        return orderDao.getBySecureId(secureId).orElseThrow();
    }

    private BigDecimal calculateDeliveryCost() {
        return BigDecimal.TEN;
    }
}
