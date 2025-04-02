package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.model.Order;

import java.util.Optional;

public class ArrayListOrderDao extends ArrayListGenericDao<Order> implements OrderDao {
    private static ArrayListOrderDao instance;

    private ArrayListOrderDao() {
        super();
    }

    public static synchronized ArrayListOrderDao getInstance() {
        if (instance == null) {
            instance = new ArrayListOrderDao();
        }
        return instance;
    }

    @Override
    public Optional<Order> getBySecureId(String secureId) {
        return list.stream()
                .filter(order -> secureId.equals(order.getSecureId()))
                .findFirst()
                .map(Order::clone);
    }
}
