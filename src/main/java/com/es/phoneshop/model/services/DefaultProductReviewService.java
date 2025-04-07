package com.es.phoneshop.model.services;

import com.es.phoneshop.model.dao.HashMapProductReviewDao;
import com.es.phoneshop.model.dao.ProductReviewDao;
import com.es.phoneshop.model.model.ProductReview;

import java.util.List;

public class DefaultProductReviewService implements ProductReviewService {
    private ProductReviewDao productReviewDao;
    private ProductService productService;
    private static DefaultProductReviewService instance;

    private DefaultProductReviewService() {
        productReviewDao = HashMapProductReviewDao.getInstance();
        productService = DefaultProductService.getInstance();
    }

    public static synchronized DefaultProductReviewService getInstance() {
        if (instance == null) {
            instance = new DefaultProductReviewService();
        }
        return instance;
    }

    @Override
    public List<ProductReview> getProductReviews(Long productId) {
        return productReviewDao.getProductReviews(productId);
    }

    @Override
    public void saveProductReview(Long productId, ProductReview productReview) {
        productReviewDao.saveProductReview(productId, productReview);
        productService.updateAverageRating(productId, getAverageRating(productId));
    }

    @Override
    public void deleteProductReviews(Long productReviewId) {
        productReviewDao.deleteProductReviews(productReviewId);
    }

    @Override
    public double getAverageRating(Long productId) {
        return productReviewDao.getAverageRating(productId);
    }
}
