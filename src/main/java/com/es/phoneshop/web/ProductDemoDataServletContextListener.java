package com.es.phoneshop.web;

import com.es.phoneshop.model.services.ProductService;
import com.es.phoneshop.model.helpers.utils.ProductUtils;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;


public class ProductDemoDataServletContextListener implements ServletContextListener {
    private ProductService productService;

    public ProductDemoDataServletContextListener() {
        productService = ProductService.getInstance();
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        if (Boolean.parseBoolean(event.getServletContext().getInitParameter("productDemoDataEnabled"))) {
            ProductUtils.getSampleProducts().forEach(product -> productService.save(product));
        }
    }
}
