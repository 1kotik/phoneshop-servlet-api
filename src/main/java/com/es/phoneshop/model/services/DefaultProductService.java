package com.es.phoneshop.model.services;

import com.es.phoneshop.model.model.Product;
import com.es.phoneshop.model.dao.ArrayListProductDao;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.helpers.enums.SortCriteria;
import com.es.phoneshop.model.helpers.enums.SortOrder;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DefaultProductService implements ProductService {
    private ProductDao productDao;
    private static DefaultProductService productService;

    private DefaultProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public static synchronized DefaultProductService getInstance() {
        if (productService == null) {
            productService = new DefaultProductService(ArrayListProductDao.getInstance());
        }
        return productService;
    }

    @Override
    public List<Product> findProducts(String query, String sortCriteria, String sortOrder) {
        return productDao.findProducts(query == null ? "" : query,
                SortCriteria.valueOf(sortCriteria == null ? "QUERY_MATCH_NUMBER" : sortCriteria.toUpperCase()),
                SortOrder.valueOf(sortOrder == null ? "ASC" : sortOrder.toUpperCase()));
    }

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public Product getProduct(Long id) {
        return productDao.getProduct(id).orElseThrow();
    }

    @Override
    public void delete(Long id) {
        productDao.delete(id);
    }

    @Override
    public void updateRecentlyViewedProducts(LinkedList<Product> recentlyViewedProducts, Product product) {
        Optional<Product> sameOptionalProduct = recentlyViewedProducts.stream()
                .filter(recentlyViewedProduct -> product.getId().equals(recentlyViewedProduct.getId()))
                .findFirst();
        sameOptionalProduct.ifPresentOrElse(recentlyViewedProducts::remove,
                () -> removeLastFromRecentlyViewedProducts(recentlyViewedProducts));
        recentlyViewedProducts.addFirst(product);
    }

    private void removeLastFromRecentlyViewedProducts(LinkedList<Product> recentlyViewedProducts){
        if (recentlyViewedProducts.size() == RECENTLY_VIEWED_PRODUCTS_LIST_SIZE) {
            recentlyViewedProducts.removeLast();
        }
    }
}
