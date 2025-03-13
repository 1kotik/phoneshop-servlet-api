package com.es.phoneshop.web;

import com.es.phoneshop.model.helpers.utils.AppConstants;
import com.es.phoneshop.model.services.DefaultProductService;
import com.es.phoneshop.model.helpers.utils.ProductUtils;
import com.es.phoneshop.model.services.ProductService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ProductDemoDataServletContextListener implements ServletContextListener {
    private ProductService productService;

    public ProductDemoDataServletContextListener() {
        productService = DefaultProductService.getInstance();
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        if (Boolean.parseBoolean(event.getServletContext().getInitParameter(AppConstants.Parameters.PRODUCT_DEMO_DATA_ENABLED))) {
            ProductUtils.getSampleProducts().forEach(product -> productService.save(product));
        }
    }
}
