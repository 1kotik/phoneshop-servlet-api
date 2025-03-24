package com.es.phoneshop.web;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String sortCriteria = request.getParameter("sortCriteria");
        String sortOrder = request.getParameter("order");
        
        request.setAttribute("recentlyViewedProducts", HttpSessionUtils.getRecentlyViewedProductsFromSession(request.getSession()));
        request.setAttribute("products", productService.findProducts(query, sortCriteria, sortOrder));
        request.setAttribute("cart", HttpSessionUtils.getCartFromSession(request.getSession()));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

}
