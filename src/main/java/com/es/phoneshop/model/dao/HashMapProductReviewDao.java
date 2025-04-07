package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.model.ProductReview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapProductReviewDao implements ProductReviewDao {
    private Map<Long, List<ProductReview>> productReviews;
    private static HashMapProductReviewDao instance;

    private HashMapProductReviewDao() {
        productReviews = new HashMap<>();
    }

    public static HashMapProductReviewDao getInstance() {
        if (instance == null) {
            instance = new HashMapProductReviewDao();
        }
        return instance;
    }

    @Override
    public synchronized List<ProductReview> getProductReviews(Long productId) {
        List<ProductReview> reviews = productReviews.get(productId);
        if (reviews != null) {
            return reviews.stream()
                    .map(ProductReview::clone)
                    .toList();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public synchronized void saveProductReview(Long productId, ProductReview productReview) {
        List<ProductReview> reviews = productReviews.computeIfAbsent(productId, k -> new ArrayList<>());
        reviews.add(productReview);
    }

    @Override
    public synchronized void deleteProductReviews(Long productId) {
        productReviews.remove(productId);
    }

    @Override
    public double getAverageRating(Long productId) {
        List<ProductReview> reviews = productReviews.get(productId);
        if (reviews != null) {
            return reviews.stream()
                    .mapToInt(ProductReview::getRating)
                    .average().orElse(0.0);
        } else {
            return 0.0;
        }
    }

}
