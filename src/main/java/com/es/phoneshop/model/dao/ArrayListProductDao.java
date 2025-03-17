package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.dto.Product;
import com.es.phoneshop.model.helpers.comparators.ProductDescriptionComparator;
import com.es.phoneshop.model.helpers.utils.ProductQueryUtils;
import com.es.phoneshop.model.helpers.enums.SortCriteria;
import com.es.phoneshop.model.helpers.enums.SortOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ArrayListProductDao implements ProductDao {
    private Long currentProductId;
    private ArrayList<Product> products;
    private Map<SortCriteria, Comparator<Product>> comparators;
    private static ArrayListProductDao instance;

    private ArrayListProductDao() {
        products = new ArrayList<>();
        currentProductId = 0L;
        comparators = new EnumMap<>(SortCriteria.class);

        comparators.put(SortCriteria.DESCRIPTION, Comparator.comparing(Product::getDescription));
        comparators.put(SortCriteria.PRICE, Comparator.comparing(Product::getPrice));
        comparators.put(SortCriteria.QUERY_MATCH_NUMBER, new ProductDescriptionComparator());
    }

    public static synchronized ArrayListProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    @Override
    public synchronized Optional<Product> getProduct(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot search. Product ID is null");
        }

        return products.stream()
                .filter(product -> id.equals(product.getId()))
                .findFirst()
                .map(Product::clone);
    }

    @Override
    public synchronized List<Product> findProducts(String query, SortCriteria sortCriteria, SortOrder sortOrder) {
        String[] queryParts = query.trim().replaceAll(" +", " ").toLowerCase().split(" ");
        return new ArrayList<>(products.stream()
                .filter(product -> ProductQueryUtils.getQueryMatchNumber(product.getDescription(), queryParts) != 0)
                .sorted(getProductComparator(sortCriteria, sortOrder, queryParts))
                .map(Product::clone)
                .toList());
    }

    @Override
    public synchronized void save(Product product) {
        Optional<Product> productToUpdate;
        if (isProductValidToSave(product)) {
            throw new IllegalArgumentException("Fill in all parameters correctly!");
        }

        productToUpdate = getProductToUpdate(product);
        if (productToUpdate.isPresent()) {
            products.set(products.indexOf(productToUpdate.get()), product);

        } else {
            product.setId(++currentProductId);
            products.add(product);
        }
    }

    @Override
    public synchronized void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot delete. Product ID is null");
        }

        products.removeIf(product -> id.equals(product.getId()));
    }

    private boolean isProductValidToSave(Product product) {
        return product.getDescription().isEmpty()
                || product.getPrice() == null
                || product.getPrice().compareTo(BigDecimal.ZERO) < 0
                || product.getStock() < 0
                || product.getCode().isEmpty()
                || product.getImageUrl().isEmpty();
    }

    private Optional<Product> getProductToUpdate(Product product) {
        if (product.getId() != null) {
            return products.stream()
                    .filter(listedProduct -> product.getId().equals(listedProduct.getId()))
                    .findFirst();
        }
        return Optional.empty();
    }

    private Comparator<Product> getProductComparator(SortCriteria sortCriteria, SortOrder sortOrder, String[] queryParts) {
        ProductDescriptionComparator.setQueryParts(queryParts);
        Comparator<Product> comparator = comparators.get(sortCriteria);

        if (sortOrder.equals(SortOrder.DESC)) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

}
