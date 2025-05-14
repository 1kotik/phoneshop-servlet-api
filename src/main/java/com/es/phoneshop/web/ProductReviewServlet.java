package com.es.phoneshop.web;

import com.es.phoneshop.model.helpers.utils.AppConstants;
import com.es.phoneshop.model.helpers.utils.HttpSessionUtils;
import com.es.phoneshop.model.model.ProductReview;
import com.es.phoneshop.model.services.DefaultProductReviewService;
import com.es.phoneshop.model.services.ProductReviewService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class ProductReviewServlet extends HttpServlet {
    private ProductReviewService productReviewService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productReviewService = DefaultProductReviewService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long productId = parseProductId(request);
        ProductReview productReview = new ProductReview();
        Map<String, String> errorMessages = HttpSessionUtils.getErrorMessagesFromSession(request.getSession());

        setProductReviewDetails(productReview, request, errorMessages);

        if (errorMessages.isEmpty()) {
            productReviewService.saveProductReview(productId, productReview);
            response.sendRedirect(request.getContextPath() + "/products/" + productId
                    + "?message=" + AppConstants.Messages.REVIEW_SAVE_SUCCESS_MESSAGE);
        } else {
            response.sendRedirect(request.getContextPath() + "/products/" + productId
                    + "?message=" + AppConstants.Messages.REVIEW_SAVE_ERROR_MESSAGE);
        }
    }

    private Optional<String> validateStringParameter(String value) {
        return value == null || value.isEmpty() ? Optional.of(AppConstants.Messages.INVALID_PARAMETER_MESSAGE)
                : Optional.empty();
    }

    private Optional<String> validateRating(String rating) {
        try {
            Integer.parseInt(rating);
            return Optional.empty();
        } catch (IllegalArgumentException e) {
            return Optional.of(AppConstants.Messages.INVALID_PARAMETER_MESSAGE);
        }
    }

    private void setProductReviewDetails(ProductReview productReview, HttpServletRequest request,
                                         Map<String, String> errorMessages) {
        String firstName = request.getParameter(AppConstants.Parameters.FIRST_NAME_PARAMETER);
        String lastName = request.getParameter(AppConstants.Parameters.LAST_NAME_PARAMETER);
        String rating = request.getParameter(AppConstants.Parameters.PRODUCT_RATING_PARAMETER);
        String productReviewText = request.getParameter(AppConstants.Parameters.PRODUCT_REVIEW_TEXT_PARAMETER);

        validateStringParameter(firstName).
                ifPresentOrElse(error -> errorMessages.put(AppConstants.Parameters.FIRST_NAME_PARAMETER, error),
                        () -> productReview.getCustomer().setFirstName(firstName));
        validateStringParameter(lastName).
                ifPresentOrElse(error -> errorMessages.put(AppConstants.Parameters.LAST_NAME_PARAMETER, error),
                        () -> productReview.getCustomer().setLastName(lastName));
        validateRating(rating).
                ifPresentOrElse(error -> errorMessages.put(AppConstants.Parameters.PRODUCT_RATING_PARAMETER, error),
                        () -> productReview.setRating(Integer.parseInt(rating)));
        productReview.setText(productReviewText);

    }

    private Long parseProductId(HttpServletRequest request) {
        return Long.parseLong(request.getPathInfo().substring(1));
    }
}
