package com.es.phoneshop.model.product;


import com.es.phoneshop.model.product.exception.BadProductRequestException;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArrayListProductDaoTest {

    @Mock
    private ArrayList<Product> products;

    @InjectMocks
    private ArrayListProductDao productDao = new ArrayListProductDao(products);


    @Test
    void testFindProducts_NoResults() {
        assertThrows(ProductNotFoundException.class, () -> productDao.findProducts());
    }

    @Test
    void testFindProducts_NotEmpty() {
        when(products.stream()).thenReturn(getSampleProducts().stream());

        List<Product> result = productDao.findProducts();

        assertTrue(result.stream().allMatch(product -> BigDecimal.ZERO.compareTo(product.getPrice()) < 0));
        assertTrue(result.stream().allMatch(product -> product.getStock() > 0));
        assertTrue(result.stream().noneMatch(product -> product.getDescription().isEmpty()));
    }

    @Test
    void testGetProduct_BadRequest() {
        assertThrows(BadProductRequestException.class, () -> productDao.getProduct(null));
    }

    @Test
    void testGetProduct_NotFound() {
        when(products.stream()).thenReturn(getSampleProducts().stream());

        assertThrows(ProductNotFoundException.class, () -> productDao.getProduct(10L));
    }

    @Test
    void testGetProduct_Success() {
        Product requestedProduct = getSampleProducts().get(2);
        when(products.stream()).thenReturn(getSampleProducts().stream());

        Product result = productDao.getProduct(requestedProduct.getId());

        assertEquals(result, requestedProduct);
    }

    @Test
    void testSave_AddNew() {
        Product newProduct = getSampleProducts().get(4);
        when(products.stream()).thenReturn(getSampleProducts().stream().limit(4));

        productDao.save(newProduct);

        verify(products).add(newProduct);
    }

    @Test
    void testSave_Update() {
        Product productToUpdate = getSampleProducts().get(4);
        when(products.stream()).thenReturn(getSampleProducts().stream());

        productDao.save(productToUpdate);

        verify(products).set(products.indexOf(productToUpdate), productToUpdate);
    }

    @Test
    void testDelete_BadRequest() {
        assertThrows(BadProductRequestException.class, () -> productDao.delete(null));
    }

    @Test
    void testDelete_NotFound() {
        Long id = 10L;
        when(products.removeIf(ArgumentMatchers.<Predicate<Product>>any())).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productDao.delete(id));
    }

    @Test
    void testDelete_Success() {
        Long id = 5L;
        when(products.removeIf(ArgumentMatchers.<Predicate<Product>>any())).thenReturn(true);

        assertDoesNotThrow(() -> productDao.delete(id));
    }


    List<Product> getSampleProducts() {
        Currency usd = Currency.getInstance("USD");
        List<Product> samples = new ArrayList<>();
        samples.add(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        samples.add(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(0), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        samples.add(new Product(3L, "sgs3", "", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        samples.add(new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        samples.add(new Product(5L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        return samples;
    }

}
