package com.es.phoneshop.model.services;

import com.es.phoneshop.model.exception.ProductOutOfStockException;
import com.es.phoneshop.model.model.Cart;
import com.es.phoneshop.model.model.CartItem;
import com.es.phoneshop.model.model.Product;
import com.es.phoneshop.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultCartServiceTest {
    @Mock
    private ProductService productService;

    private DefaultCartService cartService;

    @BeforeEach
    public void setUpBeforeEach() {
        MockitoAnnotations.openMocks(this);
        cartService = new DefaultCartService(productService);
    }

    @Test
    public void shouldAddNewItem() {
        Cart cart = TestUtils.getSampleCart();
        Long productId = 6L;
        int quantity = 2;
        Product product = TestUtils.getProduct();
        product.setId(productId);
        when(productService.getProduct(productId)).thenReturn(product);

        cartService.addItem(cart, productId, quantity);

        verify(productService).getProduct(productId);
        verifyNoMoreInteractions(productService);
        assertEquals(1, cart.getItems().stream()
                .filter(productItem -> productId.equals(productItem.getProduct().getId()))
                .count());
    }

    @Test
    public void shouldThrowProductOutOfStockExceptionWhenAddNewItem() {
        Cart cart = TestUtils.getSampleCart();
        Long productId = 6L;
        int quantity = 1000;
        Product product = TestUtils.getProduct();
        product.setId(productId);
        when(productService.getProduct(productId)).thenReturn(product);

        assertThrows(ProductOutOfStockException.class, () -> cartService.addItem(cart, productId, quantity));
        verify(productService).getProduct(productId);
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void shouldAddExistingItem() {
        Cart cart = TestUtils.getSampleCart();
        Long productId = 1L;
        int oldQuantity = cart.getItems().stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .map(CartItem::getQuantity).findFirst().get();
        int quantity = 2;

        cartService.addItem(cart, productId, quantity);

        verifyNoInteractions(productService);
        assertEquals(oldQuantity + quantity, cart.getItems().stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .map(CartItem::getQuantity).findFirst().get());
        assertEquals(1, cart.getItems().stream()
                .filter(productItem -> productId.equals(productItem.getProduct().getId()))
                .count());
    }

    @Test
    public void shouldThrowProductOutOfStockExceptionWhenAddExistingItem() {
        Cart cart = TestUtils.getSampleCart();
        Long productId = 1L;
        int quantity = 1000;
        int oldQuantity = cart.getItems().stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .map(CartItem::getQuantity).findFirst().get();

        assertThrows(ProductOutOfStockException.class, () -> cartService.addItem(cart, productId, quantity));
        verifyNoInteractions(productService);
        assertEquals(oldQuantity, cart.getItems().stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .map(CartItem::getQuantity).findFirst().get());
        assertEquals(1, cart.getItems().stream()
                .filter(productItem -> productId.equals(productItem.getProduct().getId()))
                .count());
    }

}
