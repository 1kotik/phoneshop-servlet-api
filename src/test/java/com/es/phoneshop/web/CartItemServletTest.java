package com.es.phoneshop.web;

import com.es.phoneshop.model.exception.ProductOutOfStockException;
import com.es.phoneshop.model.helpers.utils.AppConstants;
import com.es.phoneshop.model.helpers.utils.HttpSessionUtils;
import com.es.phoneshop.model.model.Cart;
import com.es.phoneshop.model.services.CartService;
import com.es.phoneshop.model.services.ProductService;
import com.es.phoneshop.utils.TestUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartItemServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ProductService productService;
    @Mock
    private CartService cartService;
    @Mock
    private HttpSession session;

    @InjectMocks
    private CartItemServlet servlet = new CartItemServlet();

    @Test
    public void shouldDoPost() throws IOException {
        Cart cart = TestUtils.getSampleCart();
        Long productId = 1L;
        int quantity = 1;
        when(request.getSession()).thenReturn(session);
        when(request.getPathInfo()).thenReturn("/1");
        when(session.getAttribute(HttpSessionUtils.CART_SESSION_ATTRIBUTE)).thenReturn(cart);
        when(request.getParameter(AppConstants.Parameters.QUANTITY_PARAMETER)).thenReturn(String.valueOf(quantity));
        when(request.getLocale()).thenReturn(Locale.ROOT);

        servlet.doPost(request, response);

        verify(cartService).addItem(cart, productId, quantity);
        verify(response).sendRedirect(anyString());
        verifyNoInteractions(productService);

    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "-1"})
    public void shouldCatchParseExceptionWhenDoPost(String quantity) throws IOException {
        when(request.getParameter(AppConstants.Parameters.QUANTITY_PARAMETER)).thenReturn(quantity);
        when(request.getLocale()).thenReturn(Locale.ROOT);

        assertDoesNotThrow(() -> servlet.doPost(request, response));
        verifyNoInteractions(cartService);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void shouldCatchProductOutOfStockExceptionWhenDoPost() throws IOException {
        Cart cart = TestUtils.getSampleCart();
        Long productId = 1L;
        int quantity = 1000;
        when(request.getSession()).thenReturn(session);
        when(request.getPathInfo()).thenReturn("/1");
        when(session.getAttribute(HttpSessionUtils.CART_SESSION_ATTRIBUTE)).thenReturn(cart);
        when(request.getParameter(AppConstants.Parameters.QUANTITY_PARAMETER)).thenReturn(String.valueOf(quantity));
        when(request.getLocale()).thenReturn(Locale.ROOT);
        doThrow(new ProductOutOfStockException(10, quantity)).when(cartService).addItem(cart, productId, quantity);

        assertDoesNotThrow(() -> servlet.doPost(request, response));
        verify(cartService).addItem(cart, productId, quantity);
        verify(response).sendRedirect(anyString());
        verifyNoInteractions(productService);
    }

    @Test
    public void shouldDoDelete() throws IOException, ServletException {
        Cart cart = TestUtils.getSampleCart();
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(HttpSessionUtils.CART_SESSION_ATTRIBUTE)).thenReturn(cart);

        servlet.doDelete(request, response);

        verify(cartService).deleteItem(cart, 1L);
        verify(response).sendRedirect(request.getContextPath() + "/cart?message=" + AppConstants.Messages.ITEM_DELETE_SUCCESS_MESSAGE);
    }

    @Test
    public void shouldDoPut() throws IOException {
        String[] productIds = {"1", "2", "3", "4"};
        String[] quantities = {"1", "2", "3", "4"};
        Cart cart = TestUtils.getSampleCart();
        Map<Long, String> map = new HashMap<>();

        when(request.getParameterValues(AppConstants.Parameters.PRODUCT_ID_PARAMETER)).thenReturn(productIds);
        when(request.getParameterValues(AppConstants.Parameters.QUANTITY_PARAMETER)).thenReturn(quantities);
        when(request.getLocale()).thenReturn(Locale.ROOT);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(HttpSessionUtils.CART_SESSION_ATTRIBUTE)).thenReturn(cart);
        when(session.getAttribute(HttpSessionUtils.ERROR_MESSAGES_SESSION_ATTRIBUTE)).thenReturn(map);
        when(session.getAttribute(HttpSessionUtils.QUANTITY_VALUES_SESSION_ATTRIBUTE)).thenReturn(map);

        servlet.doPut(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void shouldCatchParseExceptionWhenDoPut() throws IOException {
        String[] productIds = {"1", "2", "3", "4"};
        String[] quantities = {"1", "-2", "invalid", "0"};
        Cart cart = TestUtils.getSampleCart();
        Map<Long, String> map = new HashMap<>();

        when(request.getParameterValues(AppConstants.Parameters.PRODUCT_ID_PARAMETER)).thenReturn(productIds);
        when(request.getParameterValues(AppConstants.Parameters.QUANTITY_PARAMETER)).thenReturn(quantities);
        when(request.getLocale()).thenReturn(Locale.ROOT);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(HttpSessionUtils.CART_SESSION_ATTRIBUTE)).thenReturn(cart);
        when(session.getAttribute(HttpSessionUtils.ERROR_MESSAGES_SESSION_ATTRIBUTE)).thenReturn(map);
        when(session.getAttribute(HttpSessionUtils.QUANTITY_VALUES_SESSION_ATTRIBUTE)).thenReturn(map);

        assertDoesNotThrow(() -> servlet.doPut(request, response));
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void shouldCatchProductOutOfStockExceptionWhenDoPut() throws IOException {
        String[] productIds = {"1", "2", "3", "4"};
        String[] quantities = {"1000", "2", "2", "2"};
        Long productId = 1L;
        int quantity = 1000;
        Cart cart = TestUtils.getSampleCart();
        Map<Long, String> map = new HashMap<>();

        when(request.getParameterValues(AppConstants.Parameters.PRODUCT_ID_PARAMETER)).thenReturn(productIds);
        when(request.getParameterValues(AppConstants.Parameters.QUANTITY_PARAMETER)).thenReturn(quantities);
        when(request.getLocale()).thenReturn(Locale.ROOT);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(HttpSessionUtils.CART_SESSION_ATTRIBUTE)).thenReturn(cart);
        when(session.getAttribute(HttpSessionUtils.ERROR_MESSAGES_SESSION_ATTRIBUTE)).thenReturn(map);
        when(session.getAttribute(HttpSessionUtils.QUANTITY_VALUES_SESSION_ATTRIBUTE)).thenReturn(map);
        doThrow(new ProductOutOfStockException(10, quantity)).when(cartService).updateItem(cart, productId, quantity);

        assertDoesNotThrow(() -> servlet.doPut(request, response));
        verify(response).sendRedirect(anyString());
    }
}
