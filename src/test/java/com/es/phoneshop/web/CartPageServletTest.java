package com.es.phoneshop.web;

import com.es.phoneshop.model.exception.ProductOutOfStockException;
import com.es.phoneshop.model.helpers.utils.AppConstants;
import com.es.phoneshop.model.helpers.utils.HttpSessionUtils;
import com.es.phoneshop.model.model.Cart;
import com.es.phoneshop.model.services.CartService;
import com.es.phoneshop.utils.TestUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartPageServletTest {
    private final Cart cart = TestUtils.getSampleCart();
    private final Map<Long, String> map = new HashMap<>();

    @Mock
    private CartService cartService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private CartPageServlet servlet = new CartPageServlet();

    @BeforeEach
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(HttpSessionUtils.CART_SESSION_ATTRIBUTE)).thenReturn(cart);
        when(session.getAttribute(HttpSessionUtils.ERROR_MESSAGES_SESSION_ATTRIBUTE)).thenReturn(map);
        when(session.getAttribute(HttpSessionUtils.QUANTITY_VALUES_SESSION_ATTRIBUTE)).thenReturn(map);
    }

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        when(request.getRequestDispatcher(AppConstants.JspFilePaths.CART_JSP)).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldDoPost() throws ServletException, IOException {
        String[] productIds = {"1", "2", "3", "4"};
        String[] quantities = {"1", "2", "3", "4"};

        when(request.getParameterValues(AppConstants.Parameters.PRODUCT_ID_PARAMETER)).thenReturn(productIds);
        when(request.getParameterValues(AppConstants.Parameters.QUANTITY_ATTRIBUTE)).thenReturn(quantities);
        when(request.getLocale()).thenReturn(Locale.ROOT);

        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void shouldCatchParseExceptionWhenDoPost() throws ServletException, IOException {
        String[] productIds = {"1", "2", "3", "4"};
        String[] quantities = {"1", "-2", "invalid", "0"};

        when(request.getParameterValues(AppConstants.Parameters.PRODUCT_ID_PARAMETER)).thenReturn(productIds);
        when(request.getParameterValues(AppConstants.Parameters.QUANTITY_ATTRIBUTE)).thenReturn(quantities);
        when(request.getLocale()).thenReturn(Locale.ROOT);

        assertDoesNotThrow(() -> servlet.doPost(request, response));
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void shouldCatchProductOutOfStockExceptionWhenDoPost() throws ServletException, IOException {
        String[] productIds = {"1", "2", "3", "4"};
        String[] quantities = {"1000", "2", "2", "2"};
        Long productId = 1L;
        int quantity = 1000;
        when(request.getParameterValues(AppConstants.Parameters.PRODUCT_ID_PARAMETER)).thenReturn(productIds);
        when(request.getParameterValues(AppConstants.Parameters.QUANTITY_ATTRIBUTE)).thenReturn(quantities);
        when(request.getLocale()).thenReturn(Locale.ROOT);
        doThrow(new ProductOutOfStockException(10, quantity)).when(cartService).updateItem(cart, productId, quantity);

        assertDoesNotThrow(() -> servlet.doPost(request, response));
        verify(response).sendRedirect(anyString());
    }
}
