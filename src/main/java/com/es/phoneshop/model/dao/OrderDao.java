package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.model.Order;

import java.util.Optional;

public interface OrderDao extends GenericDao<Order> {
    Optional<Order> getBySecureId(String secureId);
}
