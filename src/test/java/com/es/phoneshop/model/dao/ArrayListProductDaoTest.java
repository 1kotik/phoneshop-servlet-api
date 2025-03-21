package com.es.phoneshop.model.dao;


import com.es.phoneshop.model.model.Product;
import com.es.phoneshop.model.helpers.enums.SortCriteria;
import com.es.phoneshop.model.helpers.enums.SortOrder;
import com.es.phoneshop.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ArrayListProductDaoTest {

    @Mock
    private ArrayList<Product> products;

    @InjectMocks
    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    @Test
    public void shouldThrowIllegalArgumentExceptionForGetProductIfIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> productDao.getProduct(null));
    }

    @ParameterizedTest
    @MethodSource("getFindProductsArguments")
    public void shouldFindProducts(SortCriteria sortCriteria, SortOrder sortOrder) {
        when(products.stream()).thenReturn(TestUtils.getSampleProducts().stream());

        List<Product> result = productDao.findProducts("samsung", sortCriteria, sortOrder);

        assertEquals(2, result.size());
    }

    @Test
    public void shouldGetProduct() {
        Product requestedProduct = TestUtils.getSampleProducts().get(2);
        when(products.stream()).thenReturn(TestUtils.getSampleProducts().stream());

        Optional<Product> result = productDao.getProduct(requestedProduct.getId());

        assertTrue(result.isPresent());
        assertEquals(result.get(), requestedProduct);
    }

    @Test
    public void shouldSaveProductWhenIdIsNull() {
        Product newProduct = TestUtils.getSampleProducts().get(4);
        when(products.stream()).thenReturn(TestUtils.getSampleProducts().stream().limit(4));

        productDao.save(newProduct);

        verify(products).add(newProduct);
    }

    @Test
    public void shouldSaveProductWhenIdIsNotNull() {
        Product productToUpdate = TestUtils.getSampleProducts().get(4);
        when(products.stream()).thenReturn(TestUtils.getSampleProducts().stream());

        productDao.save(productToUpdate);

        verify(products).set(products.indexOf(productToUpdate), productToUpdate);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionForSaveProduct() {
        Product productToUpdate = TestUtils.getSampleProducts().get(4);
        productToUpdate.setCode("");

        assertThrows(IllegalArgumentException.class, () -> productDao.save(productToUpdate));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionForDeleteProduct() {
        assertThrows(IllegalArgumentException.class, () -> productDao.delete(null));
    }

    @Test
    public void shouldDeleteProduct() {
        Long id = 5L;
        when(products.removeIf(ArgumentMatchers.<Predicate<Product>>any())).thenReturn(true);

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
