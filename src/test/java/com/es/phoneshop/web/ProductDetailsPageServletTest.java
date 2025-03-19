package com.es.phoneshop.web;

import com.es.phoneshop.model.exception.ProductOutOfStockException;
import com.es.phoneshop.model.helpers.utils.HttpSessionUtils;
import com.es.phoneshop.model.model.Cart;
import com.es.phoneshop.model.model.Product;
import com.es.phoneshop.model.services.CartService;
import com.es.phoneshop.model.services.ProductService;
import com.es.phoneshop.utils.TestUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Locale;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductService productService;
    @Mock
    private CartService cartService;
    @Mock
    private HttpSession session;

    @InjectMocks
    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        Product product = TestUtils.getProduct();
        when(request.getPathInfo()).thenReturn("/1");
        when(productService.getProduct(1L)).thenReturn(product);
        when(request.getSession()).thenReturn(session);
        doNothing().when(request).setAttribute("product", product);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(request).getRequestDispatcher("/WEB-INF/pages/productDetails.jsp");
        verify(requestDispatcher).forward(request, response);
        verify(productService).getProduct(1L);
        verify(request).setAttribute("product", product);
    }

    @Test
    public void shouldForwardToError404PageWhenDoGet() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/1");
        when(productService.getProduct(1L)).thenThrow(new NoSuchElementException());

        assertThrows(NoSuchElementException.class, () -> servlet.doGet(request, response));
        verifyNoInteractions(requestDispatcher);
    }

    @Test
    public void shouldForwardToGenericErrorPageWhenDoGet() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/invalid");

        assertThrows(Throwable.class, () -> servlet.doGet(request, response));
        verifyNoInteractions(requestDispatcher);
        verifyNoInteractions(productService);
    }

    @Test
    public void shouldDoPost() throws ServletException, IOException {
        Cart cart = TestUtils.getSampleCart();
        Long productId = 1L;
        int quantity = 1;
        when(request.getSession()).thenReturn(session);
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getLocale()).thenReturn(Locale.ROOT);
        when(session.getAttribute(HttpSessionUtils.CART_SESSION_ATTRIBUTE)).thenReturn(cart);
        when(request.getParameter("quantity")).thenReturn(String.valueOf(quantity));

        servlet.doPost(request, response);

        verify(cartService).addItem(cart, productId, quantity);
        verify(response).sendRedirect(anyString());
        verifyNoInteractions(productService);

    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "-1"})
    public void shouldCatchParseExceptionWhenDoPost(String quantity) throws ServletException, IOException {
        when(request.getLocale()).thenReturn(Locale.ROOT);
        when(request.getParameter("quantity")).thenReturn(quantity);
        when(request.getPathInfo()).thenReturn("/1");

        assertDoesNotThrow(() -> servlet.doPost(request, response));
        verifyNoInteractions(cartService);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void shouldCatchProductOutOfStockExceptionWhenDoPost() throws ServletException, IOException {
        Cart cart = TestUtils.getSampleCart();
        Long productId = 1L;
        int quantity = 1000;
        when(request.getSession()).thenReturn(session);
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getLocale()).thenReturn(Locale.ROOT);
        when(session.getAttribute(HttpSessionUtils.CART_SESSION_ATTRIBUTE)).thenReturn(cart);
        when(request.getParameter("quantity")).thenReturn(String.valueOf(quantity));
        doThrow(new ProductOutOfStockException(10, quantity)).when(cartService).addItem(cart, productId, quantity);

        assertDoesNotThrow(() -> servlet.doPost(request, response));
        verify(cartService).addItem(cart, productId, quantity);
        verify(response).sendRedirect(anyString());
        verifyNoInteractions(productService);
    }

}
