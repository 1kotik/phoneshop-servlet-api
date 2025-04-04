package com.es.phoneshop.model.services;

import com.es.phoneshop.model.dao.ArrayListProductDao;
import com.es.phoneshop.model.model.Product;
import com.es.phoneshop.model.helpers.enums.SortCriteria;
import com.es.phoneshop.model.helpers.enums.SortOrder;
import com.es.phoneshop.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultProductServiceTest {
    @Mock
    private ArrayListProductDao productDao;

    @InjectMocks
    private DefaultProductService productService;

    @ParameterizedTest
    @MethodSource("getShouldFindProductsTestArguments")
    public void shouldFindProducts(String query, String sortCriteria, String sortOrder) {
        when(productDao.findProducts(any(), any(), any()))
                .thenReturn(TestUtils.getSampleProducts());

        List<Product> result = productService.findProducts(query, sortCriteria, sortOrder);

        assertEquals(result.size(), TestUtils.getSampleProducts().size());
        verify(productDao).findProducts(query == null ? "" : query,
                sortCriteria == null ? SortCriteria.QUERY_MATCH_NUMBER :
                        SortCriteria.valueOf(sortCriteria.toUpperCase()),
                sortOrder == null ? SortOrder.ASC : SortOrder.valueOf(sortOrder.toUpperCase()));
    }

    @Test
    public void shouldSaveProduct() {
        productService.save(any());

        verify(productDao).save(any());
    }

    @Test
    public void shouldGetProduct() {
        when(productDao.getById(1L)).thenReturn(Optional.of(TestUtils.getProduct()));

        Product result = productService.getProduct(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    public void shouldThrowNoSuchElementExceptionForGetProduct() {
        when(productDao.getById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.getProduct(1L));
    }

    @Test
    public void shouldDelete() {
        productService.delete(1L);

        verify(productDao).delete(1L);
    }

    @ParameterizedTest
    @MethodSource("getUpdateRecentlyViewedProductsArguments")
    public void shouldUpdateRecentlyViewedProducts(LinkedList<Product> recentlyViewedProducts, Product product) {
        productService.updateRecentlyViewedProducts(recentlyViewedProducts, product);

        assertEquals(3, recentlyViewedProducts.size());
        assertEquals(product.getId(), recentlyViewedProducts.getFirst().getId());
    }

    private static Stream<Arguments> getShouldFindProductsTestArguments() {
        String query = "samsung";
        String description = "description";
        String price = "price";
        String asc = "asc";
        String desc = "desc";

        return Stream.of(Arguments.of(query, description, asc),
                Arguments.of(query, description, desc),
                Arguments.of(query, price, asc),
                Arguments.of(query, price, desc),
                Arguments.of(null, description, asc),
                Arguments.of(query, null, asc),
                Arguments.of(query, description, null));
    }

    private static Stream<Arguments> getUpdateRecentlyViewedProductsArguments(){
        LinkedList<Product> recentlyViewedProducts = new LinkedList<>(TestUtils.getSampleProducts());
        return Stream.of(Arguments.of(new LinkedList<>(recentlyViewedProducts.subList(1,4)), TestUtils.getProduct()),
                Arguments.of(new LinkedList<>(recentlyViewedProducts.subList(0,3)), TestUtils.getProduct()),
                Arguments.of(new LinkedList<>(recentlyViewedProducts.subList(1,3)), TestUtils.getProduct()));
    }

}
