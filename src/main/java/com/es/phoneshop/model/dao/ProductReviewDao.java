package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.model.ProductReview;

import java.util.List;

public interface ProductReviewDao {
    List<ProductReview> getProductReviews(Long productId);

    void saveProductReview(Long productId, ProductReview productReview);

    void deleteProductReviews(Long productId);

    double getAverageRating(Long productId);
}
