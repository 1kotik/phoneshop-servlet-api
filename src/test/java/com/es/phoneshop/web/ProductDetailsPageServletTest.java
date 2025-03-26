package com.es.phoneshop.web;

import com.es.phoneshop.model.helpers.utils.AppConstants;
import com.es.phoneshop.model.model.Product;
import com.es.phoneshop.model.services.ProductService;
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
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
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
    private HttpSession session;

    @InjectMocks
    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        Product product = TestUtils.getProduct();
        when(request.getPathInfo()).thenReturn("/1");
        when(productService.getProduct(1L)).thenReturn(product);
        when(request.getSession()).thenReturn(session);
        doNothing().when(request).setAttribute(AppConstants.RequestAttributes.PRODUCT_ATTRIBUTE, product);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(request).getRequestDispatcher(AppConstants.JspFilePaths.PRODUCT_DETAILS_JSP);
        verify(requestDispatcher).forward(request, response);
        verify(productService).getProduct(1L);
        verify(request).setAttribute(AppConstants.RequestAttributes.PRODUCT_ATTRIBUTE, product);
    }

    @Test
    public void shouldForwardToError404PageWhenDoGet() {
        when(request.getPathInfo()).thenReturn("/1");
        when(productService.getProduct(1L)).thenThrow(new NoSuchElementException());

        assertThrows(NoSuchElementException.class, () -> servlet.doGet(request, response));
        verifyNoInteractions(requestDispatcher);
    }

    @Test
    public void shouldForwardToGenericErrorPageWhenDoGet() {
        when(request.getPathInfo()).thenReturn("/invalid");

        assertThrows(Throwable.class, () -> servlet.doGet(request, response));
        verifyNoInteractions(requestDispatcher);
        verifyNoInteractions(productService);
    }

}
