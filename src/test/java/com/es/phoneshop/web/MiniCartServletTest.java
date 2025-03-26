package com.es.phoneshop.web;

import com.es.phoneshop.model.helpers.utils.AppConstants;
import com.es.phoneshop.model.helpers.utils.HttpSessionUtils;
import com.es.phoneshop.model.model.Cart;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MiniCartServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private MiniCartServlet servlet = new MiniCartServlet();

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        Cart cart = TestUtils.getSampleCart();
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(HttpSessionUtils.CART_SESSION_ATTRIBUTE)).thenReturn(cart);
        when(request.getRequestDispatcher(AppConstants.JspFilePaths.MINI_CART_JSP)).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(requestDispatcher).include(request, response);
    }

}
