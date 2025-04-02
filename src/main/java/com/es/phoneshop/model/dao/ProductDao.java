package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.model.Product;
import com.es.phoneshop.model.helpers.enums.SortCriteria;
import com.es.phoneshop.model.helpers.enums.SortOrder;

import java.util.List;

public interface ProductDao extends GenericDao<Product> {
    List<Product> findProducts(String query, SortCriteria sortCriteria, SortOrder sortOrder);

    void delete(Long id);
}
