package com.es.phoneshop.web;

import com.es.phoneshop.model.helpers.utils.AppConstants;
import com.es.phoneshop.model.model.Product;
import com.es.phoneshop.model.helpers.utils.ProductUtils;
import com.es.phoneshop.model.services.ProductService;
import com.es.phoneshop.utils.TestUtils;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductDemoDataServletContextListenerTest {
    @Mock
    private ProductService productService;

    @Mock
    private ServletContextEvent event;

    @Mock
    private ServletContext context;

    @InjectMocks
    private ProductDemoDataServletContextListener productDemoDataServletContextListener
            = new ProductDemoDataServletContextListener();

    @Test
    public void shouldEnableDemoDataForContextInitialized() {
        try (MockedStatic<ProductUtils> productSupplier = Mockito.mockStatic(ProductUtils.class)) {
            productSupplier.when(ProductUtils::getSampleProducts).thenReturn(TestUtils.getSampleProducts());
            when(event.getServletContext()).thenReturn(context);
            when(context.getInitParameter(AppConstants.Parameters.PRODUCT_DEMO_DATA_ENABLED)).thenReturn("true");

            productDemoDataServletContextListener.contextInitialized(event);

            verify(productService, times(TestUtils.getSampleProducts().size())).save(any(Product.class));
        }
    }

    @Test
    public void shouldDisableDemoDataForContextInitialized() {
        when(event.getServletContext()).thenReturn(context);
        when(context.getInitParameter(AppConstants.Parameters.PRODUCT_DEMO_DATA_ENABLED)).thenReturn("false");

        productDemoDataServletContextListener.contextInitialized(event);

        verify(productService, times(0)).save(any(Product.class));
    }

}
