package com.es.phoneshop.model.services;

import com.es.phoneshop.model.dto.Product;
import com.es.phoneshop.model.dao.ArrayListProductDao;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.helpers.enums.SortCriteria;
import com.es.phoneshop.model.helpers.enums.SortOrder;

import java.util.List;

public class ProductService {
    private final ProductDao productDao;
    private static ProductService productService;

    private ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public static synchronized ProductService getInstance() {
        if (productService == null) {
            productService = new ProductService(ArrayListProductDao.getInstance());
        }
        return productService;
    }

    public List<Product> findProducts(String query, String sortCriteria, String sortOrder) {
        return productDao.findProducts(query == null ? "" : query,
                SortCriteria.valueOf(sortCriteria == null ? "QUERY_MATCH_NUMBER" : sortCriteria.toUpperCase()),
                SortOrder.valueOf(sortOrder == null ? "ASC" : sortOrder.toUpperCase()));
    }

    public void save(Product product) {
        productDao.save(product);
    }

    public Product getProduct(Long id){
        return productDao.getProduct(id).orElseThrow();
    }

    public void delete(Long id) {
        productDao.delete(id);
    }
}
