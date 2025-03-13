package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.dto.Product;
import com.es.phoneshop.model.helpers.enums.SortCriteria;
import com.es.phoneshop.model.helpers.enums.SortOrder;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Optional<Product> getProduct(Long id);
    List<Product> findProducts(String query, SortCriteria sortCriteria, SortOrder sortOrder);
    void save(Product product);
    void delete(Long id);
}
