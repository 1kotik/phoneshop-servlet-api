package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.exception.BadProductRequestException;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductListPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductListPageServlet servlet = new ProductListPageServlet();


    @Test
    void testDoGet_FindProducts() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
        verify(productDao).findProducts();
    }


    @Test
    void testDoGet_deleteSuccess() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("product-id")).thenReturn("1");

        servlet.doGet(request, response);

        verify(response).sendRedirect(anyString());
        verify(productDao).delete(1L);
    }

    @Test
    void testDoGet_DeleteCatchException() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("product-id")).thenReturn("1");
        doThrow(ProductNotFoundException.class).when(productDao).delete(1L);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(productDao).delete(1L);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoGet_getProductSuccess() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("get-product");
        when(request.getParameter("product-id")).thenReturn("1");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(productDao).getProduct(1L);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoGet_getProductNotFound() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("get-product");
        when(request.getParameter("product-id")).thenReturn("1");
        doThrow(ProductNotFoundException.class).when(productDao).getProduct(1L);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(productDao).getProduct(1L);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoGet_redirectToAddProductPage() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("redirect-to-add-product-page");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @ParameterizedTest
    @ValueSource(strings = {"update", "add"})
    void testDoPost_saveSuccess(String action) throws ServletException, IOException {
        String testValue = "123";
        when(request.getParameter("save-action")).thenReturn(action);
        when(request.getParameter("product-code")).thenReturn(testValue);
        when(request.getParameter("product-description")).thenReturn(testValue);
        when(request.getParameter("product-price")).thenReturn(testValue);
        when(request.getParameter("product-currency")).thenReturn("USD");
        when(request.getParameter("product-stock")).thenReturn(testValue);
        when(request.getParameter("product-image-url")).thenReturn(testValue);
        if(action.equals("update")) {
            when(request.getParameter("product-id")).thenReturn(testValue);
        }

        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"update", "add"})
    void testDoPost_saveCatchNPE(String action) throws ServletException, IOException {
        String testValue = "123";
        when(request.getParameter("save-action")).thenReturn(action);
        when(request.getParameter("product-code")).thenReturn(testValue);
        when(request.getParameter("product-description")).thenReturn(testValue);
        when(request.getParameter("product-price")).thenReturn(testValue);
        when(request.getParameter("product-currency")).thenReturn(null);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        if(action.equals("update")) {
            when(request.getParameter("product-id")).thenReturn(testValue);
        }

        servlet.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
    }


    @ParameterizedTest
    @ValueSource(strings = {"update", "add"})
    void testDoPost_saveCatchIllegalArgumentException(String action) throws ServletException, IOException {
        String testValue = "123";
        when(request.getParameter("save-action")).thenReturn(action);
        when(request.getParameter("product-code")).thenReturn(testValue);
        when(request.getParameter("product-description")).thenReturn(testValue);
        when(request.getParameter("product-price")).thenReturn(testValue);
        when(request.getParameter("product-currency")).thenReturn("illegalArgument");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        if(action.equals("update")) {
            when(request.getParameter("product-id")).thenReturn(testValue);
        }

        servlet.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @ParameterizedTest
    @ValueSource(strings = {"update", "add"})
    void testDoPost_saveCatchBadProductRequestException(String action) throws ServletException, IOException {
        String testValue = "123";
        when(request.getParameter("save-action")).thenReturn(action);
        when(request.getParameter("product-code")).thenReturn("");
        when(request.getParameter("product-description")).thenReturn(testValue);
        when(request.getParameter("product-price")).thenReturn(testValue);
        when(request.getParameter("product-currency")).thenReturn("USD");
        when(request.getParameter("product-stock")).thenReturn(testValue);
        when(request.getParameter("product-image-url")).thenReturn(testValue);
        doThrow(BadProductRequestException.class).when(productDao).save(any());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        if(action.equals("update")) {
            when(request.getParameter("product-id")).thenReturn(testValue);
        }

        servlet.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
    }

}