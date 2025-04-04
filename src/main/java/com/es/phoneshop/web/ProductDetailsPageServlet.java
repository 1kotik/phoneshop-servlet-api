package com.es.phoneshop.web;

import com.es.phoneshop.model.helpers.utils.AppConstants;
import com.es.phoneshop.model.helpers.utils.HttpSessionUtils;
import com.es.phoneshop.model.model.Product;
import com.es.phoneshop.model.services.DefaultProductService;
import com.es.phoneshop.model.services.ProductService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.LinkedList;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productService = DefaultProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long productId = parseProductId(request);
        Product product = productService.getProduct(productId);
        LinkedList<Product> recentlyViewedProducts = HttpSessionUtils
                .getRecentlyViewedProductsFromSession(request.getSession());

        productService.updateRecentlyViewedProducts(recentlyViewedProducts, product);
        request.setAttribute(AppConstants.RequestAttributes.PRODUCT_ATTRIBUTE, product);
        request.setAttribute(AppConstants.RequestAttributes.RECENTLY_VIEWED_PRODUCTS_ATTRIBUTE, recentlyViewedProducts);
        request.getRequestDispatcher(AppConstants.JspFilePaths.PRODUCT_DETAILS_JSP).forward(request, response);
    }

    private Long parseProductId(HttpServletRequest request) {
        return Long.parseLong(request.getPathInfo().substring(1));
    }
}
