package com.es.phoneshop.web;

import com.es.phoneshop.model.helpers.utils.AppConstants;
import com.es.phoneshop.model.helpers.utils.HttpSessionUtils;
import com.es.phoneshop.model.services.DefaultProductService;
import com.es.phoneshop.model.services.ProductService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productService = DefaultProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter(AppConstants.Parameters.QUERY_PARAMETER);
        String sortCriteria = request.getParameter(AppConstants.Parameters.SORT_CRITERIA_PARAMETER);
        String sortOrder = request.getParameter(AppConstants.Parameters.ORDER_PARAMETER);

        request.setAttribute(AppConstants.RequestAttributes.RECENTLY_VIEWED_PRODUCTS_ATTRIBUTE,
                HttpSessionUtils.getRecentlyViewedProductsFromSession(request.getSession()));
        request.setAttribute(AppConstants.RequestAttributes.PRODUCTS_ATTRIBUTE,
                productService.findProducts(query, sortCriteria, sortOrder));
        request.getRequestDispatcher(AppConstants.JspFilePaths.PRODUCT_LIST_JSP).forward(request, response);
    }

}
