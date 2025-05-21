package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.helpers.enums.SearchMethod;
import com.es.phoneshop.model.model.Product;
import com.es.phoneshop.model.helpers.enums.SortCriteria;
import com.es.phoneshop.model.helpers.enums.SortOrder;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDao extends GenericDao<Product> {
    List<Product> findProducts(String query, SortCriteria sortCriteria, SortOrder sortOrder);
    List<Product> findProductsByAdvancedSearch(String query, SearchMethod searchMethod, BigDecimal minPrice,
                                               BigDecimal maxPrice);
    void delete(Long id);
    List<Product> getAllProducts();
}
