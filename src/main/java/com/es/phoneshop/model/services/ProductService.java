package com.es.phoneshop.model.services;

import com.es.phoneshop.model.model.Product;

import java.util.LinkedList;
import java.util.List;

public interface ProductService {
    int RECENTLY_VIEWED_PRODUCTS_LIST_SIZE = 3;

    List<Product> findProducts(String query, String sortCriteria, String sortOrder);

    void save(Product product);

    Product getProduct(Long id);

    void delete(Long id);

    void updateRecentlyViewedProducts(LinkedList<Product> recentlyViewedProducts, Product product);

    void updateAverageRating(Long id, double averageRating);
}
