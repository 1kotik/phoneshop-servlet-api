package com.es.phoneshop.web;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(HttpSessionUtils.CART_SESSION_ATTRIBUTE)).thenReturn(cart);
        when(session.getAttribute(HttpSessionUtils.ERROR_MESSAGES_SESSION_ATTRIBUTE)).thenReturn(map);
        when(session.getAttribute(HttpSessionUtils.QUANTITY_VALUES_SESSION_ATTRIBUTE)).thenReturn(map);
        when(request.getRequestDispatcher(AppConstants.JspFilePaths.CART_JSP)).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

}
