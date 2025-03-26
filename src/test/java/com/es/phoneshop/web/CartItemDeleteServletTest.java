package com.es.phoneshop.web;


import com.es.phoneshop.model.helpers.utils.AppConstants;
import com.es.phoneshop.model.helpers.utils.HttpSessionUtils;
import com.es.phoneshop.model.model.Cart;
import com.es.phoneshop.model.services.CartService;
import com.es.phoneshop.utils.TestUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartItemDeleteServletTest {
    @Mock
    private CartService cartService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    @InjectMocks
    private CartItemDeleteServlet servlet = new CartItemDeleteServlet();

    @Test
    public void shouldDoPost() throws IOException {
        Cart cart = TestUtils.getSampleCart();
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(HttpSessionUtils.CART_SESSION_ATTRIBUTE)).thenReturn(cart);

        servlet.doPost(request, response);

        verify(cartService).deleteItem(cart, 1L);
        verify(response).sendRedirect(request.getContextPath() + "/cart?message=" + AppConstants.Messages.ITEM_DELETE_SUCCESS_MESSAGE);
    }
}
