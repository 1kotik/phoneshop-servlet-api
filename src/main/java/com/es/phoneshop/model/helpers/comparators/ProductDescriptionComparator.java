package com.es.phoneshop.model.helpers.comparators;

import com.es.phoneshop.model.model.Product;
import com.es.phoneshop.model.helpers.utils.ProductQueryUtils;


import java.util.Comparator;


public class ProductDescriptionComparator implements Comparator<Product> {
    private static String[] queryParts;

    public static void setQueryParts(String[] queryParts){
        ProductDescriptionComparator.queryParts = queryParts;
    }
    @Override
    public int compare(Product product1, Product product2) {
        long product1QueryMatchNumber = ProductQueryUtils.getQueryMatchNumber(product1.getDescription(), queryParts);
        long product2QueryMatchNumber = ProductQueryUtils.getQueryMatchNumber(product2.getDescription(), queryParts);

        return Long.compare(product2QueryMatchNumber, product1QueryMatchNumber);
    }
}
