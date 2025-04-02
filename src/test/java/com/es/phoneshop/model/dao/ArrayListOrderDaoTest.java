package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.model.Order;
import com.es.phoneshop.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArrayListOrderDaoTest {
    private ArrayListOrderDao orderDao = ArrayListOrderDao.getInstance();

    @BeforeEach
    public void setUp() {
        orderDao.list.clear();
        orderDao.currentId = 0L;
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionForGetOrderIfIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> orderDao.getById(null));
    }

    @Test
    public void shouldGetOrder() {
        Order requestedOrder = TestUtils.getSampleOrders().get(1);
        TestUtils.getSampleOrders().stream().forEach(orderDao::save);

        Optional<Order> result = orderDao.getById(requestedOrder.getId());

        assertEquals(result.get().getId(), requestedOrder.getId());
    }

    @Test
    public void shouldSaveOrderWhenIdIsNull() {
        Order newOrder = TestUtils.getSampleOrders().get(1);
        TestUtils.getSampleOrders().stream().limit(1).forEach(orderDao::save);

        orderDao.save(newOrder);

        assertTrue(orderDao.getById(newOrder.getId()).isPresent());
    }

    @Test
    public void shouldSaveOrderWhenIdIsNotNull() {
        Order orderToUpdate = TestUtils.getSampleOrders().get(1);
        TestUtils.getSampleOrders().stream().forEach(orderDao::save);

        orderDao.save(orderToUpdate);

        assertTrue(orderDao.getById(orderToUpdate.getId()).isPresent());
        assertEquals(1, orderDao.list.stream()
                .filter(product -> orderToUpdate.getId().equals(product.getId()))
                .count());
    }

    @Test
    public void shouldGetOrderBySecureId() {
        Order requestedOrder = TestUtils.getSampleOrders().get(1);
        TestUtils.getSampleOrders().stream().forEach(orderDao::save);

        Optional<Order> result = orderDao.getBySecureId(requestedOrder.getSecureId());

        assertTrue(result.isPresent());
        assertEquals(result.get().getSecureId(), requestedOrder.getSecureId());
    }
}
