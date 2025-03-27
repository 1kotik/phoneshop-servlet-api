package com.es.phoneshop.web;

import com.es.phoneshop.model.helpers.utils.AppConstants;
import com.es.phoneshop.model.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductListPageServletTest {
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
    private ProductListPageServlet servlet = new ProductListPageServlet();


    @ParameterizedTest
    @MethodSource("getDoGetTestArguments")
    public void shouldDoGet(String query, String sortCriteria, String sortOrder) throws ServletException, IOException {
        when(request.getParameter(AppConstants.Parameters.QUERY_PARAMETER)).thenReturn(query);
        when(request.getParameter(AppConstants.Parameters.SORT_CRITERIA_PARAMETER)).thenReturn(sortCriteria);
        when(request.getParameter(AppConstants.Parameters.ORDER_PARAMETER)).thenReturn(sortOrder);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
        verify(productService).findProducts(query, sortCriteria, sortOrder);
    }

    private static Stream<Arguments> getDoGetTestArguments() {
        String query = "samsung";
        String description = "description";
        String price = "price";
        String asc = "asc";
        String desc = "desc";

        return Stream.of(Arguments.of(query, description, asc),
                Arguments.of(query, description, desc),
                Arguments.of(query, price, asc),
                Arguments.of(query, price, desc),
                Arguments.of(null, description, asc,
                        Arguments.of(query, null, asc),
                        Arguments.of(query, description, null)));
    }
}
