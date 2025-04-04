package com.es.phoneshop.web;

import com.es.phoneshop.model.helpers.utils.AppConstants;
import com.es.phoneshop.model.services.DefaultProductService;
import com.es.phoneshop.model.services.ProductService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProductPriceHistoryPageServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productService = DefaultProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long productId = Long.parseLong(request.getPathInfo().substring(1));
        request.setAttribute(AppConstants.RequestAttributes.PRICE_HISTORY_ATTRIBUTE,
                productService.getProduct(productId).getPriceHistory());
        request.getRequestDispatcher(AppConstants.JspFilePaths.PRODUCT_PRICE_HISTORY_JSP).forward(request, response);
    }
}
