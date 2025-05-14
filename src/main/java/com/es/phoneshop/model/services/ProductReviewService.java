package com.es.phoneshop.model.services;

import com.es.phoneshop.model.model.ProductReview;

import java.util.List;

public interface ProductReviewService {
    List<ProductReview> getProductReviews(Long productId);

    void saveProductReview(Long productId, ProductReview productReview);

    void deleteProductReviews(Long productReviewId);

    double getAverageRating(Long productId);
}
