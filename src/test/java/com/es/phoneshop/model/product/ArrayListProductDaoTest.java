package com.es.phoneshop.model.product;


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
import java.util.Optional;
import java.util.function.Predicate;

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
    public void testGetProductThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> productDao.getProduct(null));
    }

    @Test
    public void testGetProductSuccess() {
        Product requestedProduct = getSampleProducts().get(2);
        when(products.stream()).thenReturn(getSampleProducts().stream());

        Optional<Product> result = productDao.getProduct(requestedProduct.getId());

        assertTrue(result.isPresent());
        assertEquals(result.get(), requestedProduct);
    }

    @Test
    public void testSaveAddNew() {
        Product newProduct = getSampleProducts().get(4);
        when(products.stream()).thenReturn(getSampleProducts().stream().limit(4));

        productDao.save(newProduct);

        verify(products).add(newProduct);
    }

    @Test
    public void testSaveUpdate() {
        Product productToUpdate = getSampleProducts().get(4);
        when(products.stream()).thenReturn(getSampleProducts().stream());

        productDao.save(productToUpdate);

        verify(products).set(products.indexOf(productToUpdate), productToUpdate);
    }

    @Test
    public void testSaveThrowsIllegalArgumentException() {
        Product productToUpdate = getSampleProducts().get(4);
        productToUpdate.setCode("");

        assertThrows(IllegalArgumentException.class, () -> productDao.save(productToUpdate));
    }

    @Test
    public void testDeleteThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> productDao.delete(null));
    }

    @Test
    public void testDeleteSuccess() {
        Long id = 5L;
        when(products.removeIf(ArgumentMatchers.<Predicate<Product>>any())).thenReturn(true);

        assertDoesNotThrow(() -> productDao.delete(id));
    }


    private List<Product> getSampleProducts() {
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
