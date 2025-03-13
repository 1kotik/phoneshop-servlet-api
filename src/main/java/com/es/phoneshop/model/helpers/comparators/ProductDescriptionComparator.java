package com.es.phoneshop.model.helpers.comparators;

import com.es.phoneshop.model.dto.Product;
import com.es.phoneshop.model.helpers.utils.ProductQueryUtils;


import java.util.Comparator;


public class ProductDescriptionComparator implements Comparator<Product> {
    @Override
    public int compare(Product product1, Product product2) {
        long product1QueryMatchNumber = ProductQueryUtils.getQueryMatchNumber(product1.getDescription());
        long product2QueryMatchNumber = ProductQueryUtils.getQueryMatchNumber(product2.getDescription());

        return Long.compare(product2QueryMatchNumber, product1QueryMatchNumber);
    }
}
