package com.es.phoneshop.model.dao;


import com.es.phoneshop.model.model.Product;
import com.es.phoneshop.model.helpers.enums.SortCriteria;
import com.es.phoneshop.model.helpers.enums.SortOrder;
import com.es.phoneshop.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ArrayListProductDaoTest {
    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    @BeforeEach
    public void setUp() {
        productDao.list.clear();
        productDao.currentId = 0L;
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionForGetProductIfIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> productDao.getById(null));
    }

    @ParameterizedTest
    @MethodSource("getFindProductsArguments")
    public void shouldFindProducts(SortCriteria sortCriteria, SortOrder sortOrder) {
        TestUtils.getSampleProducts().stream().forEach(productDao::save);

        List<Product> result = productDao.findProducts("samsung", sortCriteria, sortOrder);

        assertEquals(2, result.size());
    }

    @Test
    public void shouldGetProduct() {
        Product requestedProduct = TestUtils.getSampleProducts().get(2);
        TestUtils.getSampleProducts().stream().forEach(productDao::save);

        Optional<Product> result = productDao.getById(requestedProduct.getId());

        assertEquals(result.get().getId(), requestedProduct.getId());
    }

    @Test
    public void shouldSaveProductWhenIdIsNull() {
        Product newProduct = TestUtils.getSampleProducts().get(4);
        TestUtils.getSampleProducts().stream().limit(4).forEach(productDao::save);

        productDao.save(newProduct);

        assertTrue(productDao.getById(newProduct.getId()).isPresent());
    }

    @Test
    public void shouldSaveProductWhenIdIsNotNull() {
        Product productToUpdate = TestUtils.getSampleProducts().get(4);
        TestUtils.getSampleProducts().stream().forEach(productDao::save);

        productDao.save(productToUpdate);

        assertTrue(productDao.getById(productToUpdate.getId()).isPresent());
        assertEquals(1, productDao.list.stream()
                .filter(product -> productToUpdate.getId().equals(product.getId()))
                .count());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionForDeleteProduct() {
        assertThrows(IllegalArgumentException.class, () -> productDao.delete(null));
    }

    @Test
    public void shouldDeleteProduct() {
        Long id = 5L;

        assertDoesNotThrow(() -> productDao.delete(id));
    }


    private static Stream<Arguments> getFindProductsArguments() {
        return Stream.of(Arguments.of(SortCriteria.DESCRIPTION, SortOrder.ASC),
                Arguments.of(SortCriteria.DESCRIPTION, SortOrder.DESC),
                Arguments.of(SortCriteria.PRICE, SortOrder.ASC),
                Arguments.of(SortCriteria.PRICE, SortOrder.DESC),
                Arguments.of(SortCriteria.QUERY_MATCH_NUMBER, SortOrder.ASC));
    }

}
