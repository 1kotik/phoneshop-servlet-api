package com.es.phoneshop.web;

import com.es.phoneshop.model.helpers.enums.SearchMethod;
import com.es.phoneshop.model.helpers.utils.AppConstants;
import com.es.phoneshop.model.model.Product;
import com.es.phoneshop.model.services.DefaultProductService;
import com.es.phoneshop.model.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdvancedSearchPageServlet extends HttpServlet {
    private ProductService productService;
    private Map<String, String> errorMessages;

    @Override
    public void init() throws ServletException {
        super.init();
        productService = DefaultProductService.getInstance();
        errorMessages = new HashMap<>();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setProductsAttribute(request);
        request.setAttribute(AppConstants.RequestAttributes.ERRORS_ATTRIBUTE, errorMessages);
        request.getRequestDispatcher(AppConstants.JspFilePaths.ADVANCED_SEARCH_JSP).forward(request, response);
        errorMessages.clear();
    }

    private double parseDouble(String parameter, String stringValue) {
        double value = 0.0;
        try {
            value = Double.parseDouble(stringValue);
        } catch (NullPointerException e) {
            return 0.0;
        } catch (NumberFormatException e) {
            errorMessages.put(parameter, AppConstants.Messages.NOT_A_NUMBER_ERROR_MESSAGE);
        }
        return value;
    }

    private void setProductsAttribute(HttpServletRequest request) {
        String query = request.getParameter(AppConstants.Parameters.QUERY_PARAMETER);
        String searchMethod = request.getParameter(AppConstants.Parameters.SEARCH_METHOD_PARAMETER);
        String stringMinPrice = request.getParameter(AppConstants.Parameters.MIN_PRICE_PARAMETER);
        String stringMaxPrice = request.getParameter(AppConstants.Parameters.MAX_PRICE_PARAMETER);

        if (isParametersAreEmpty(query, stringMinPrice, stringMaxPrice)) {
            findProductsWhenParametersNull(request, searchMethod);
        } else {
            findProductsWhenParametersNotNull(request, query, searchMethod, stringMinPrice, stringMaxPrice);
        }
    }

    private void findProductsWhenParametersNotNull(HttpServletRequest request, String query, String stringSearchMethod,
                                                   String stringMinPrice, String stringMaxPrice) {
        double minPrice = parseDouble(AppConstants.Parameters.MIN_PRICE_PARAMETER, stringMinPrice);
        double maxPrice = parseDouble(AppConstants.Parameters.MAX_PRICE_PARAMETER, stringMaxPrice);
        SearchMethod searchMethod = SearchMethod.getSearchMethod(stringSearchMethod);

        if (errorMessages.isEmpty()) {
            request.setAttribute(AppConstants.RequestAttributes.PRODUCTS_ATTRIBUTE,
                    productService.findProductsByAdvancedSearch(query, searchMethod, minPrice, maxPrice));
        } else {
            request.setAttribute(AppConstants.RequestAttributes.PRODUCTS_ATTRIBUTE, new ArrayList<Product>());
        }

    }

    private void findProductsWhenParametersNull(HttpServletRequest request, String searchMethod) {
        if (searchMethod == null) {
            request.setAttribute(AppConstants.RequestAttributes.PRODUCTS_ATTRIBUTE, new ArrayList<Product>());
        } else {
            request.setAttribute(AppConstants.RequestAttributes.PRODUCTS_ATTRIBUTE, productService.getAllProducts());
        }
    }

    private boolean isParametersAreEmpty(String query, String minPrice, String maxPrice) {
        return (query == null || query.isEmpty())
                && (minPrice == null || minPrice.isEmpty())
                && (maxPrice == null || maxPrice.isEmpty());
    }
}
