package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.model.Product;
import com.es.phoneshop.model.helpers.comparators.ProductDescriptionComparator;
import com.es.phoneshop.model.helpers.utils.ProductQueryUtils;
import com.es.phoneshop.model.helpers.enums.SortCriteria;
import com.es.phoneshop.model.helpers.enums.SortOrder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ArrayListProductDao extends ArrayListGenericDao<Product> implements ProductDao {
    private Map<SortCriteria, Comparator<Product>> comparators;
    private static ArrayListProductDao instance;

    private ArrayListProductDao() {
        comparators = new EnumMap<>(SortCriteria.class);

        comparators.put(SortCriteria.DESCRIPTION, Comparator.comparing(Product::getDescription));
        comparators.put(SortCriteria.PRICE, Comparator.comparing(Product::getPrice));
        comparators.put(SortCriteria.QUERY_MATCH_NUMBER, new ProductDescriptionComparator());
        comparators.put(SortCriteria.AVERAGE_RATING, Comparator.comparing(Product::getAverageRating));
    }

    public static synchronized ArrayListProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    @Override
    public synchronized List<Product> findProducts(String query, SortCriteria sortCriteria, SortOrder sortOrder) {
        String[] queryParts = query.trim().replaceAll(" +", " ").toLowerCase().split(" ");
        return new ArrayList<>(list.stream()
                .filter(product -> ProductQueryUtils.getQueryMatchNumber(product.getDescription(), queryParts) != 0)
                .sorted(getProductComparator(sortCriteria, sortOrder, queryParts))
                .map(Product::clone)
                .toList());
    }

    @Override
    public synchronized void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot delete. Product ID is null");
        }

        list.removeIf(product -> id.equals(product.getId()));
    }

    @Override
    public void updateAverageRating(Long id, double averageRating) {
        list.stream().filter(product -> id.equals(product.getId()))
                .forEach(product -> product.setAverageRating(averageRating));
    }

    private Comparator<Product> getProductComparator(SortCriteria sortCriteria, SortOrder sortOrder,
                                                     String[] queryParts) {
        ProductDescriptionComparator.setQueryParts(queryParts);
        Comparator<Product> comparator = comparators.get(sortCriteria);

        if (sortOrder.equals(SortOrder.DESC)) {
            comparator = comparator.reversed();
        }

        return comparator;
    }
}
