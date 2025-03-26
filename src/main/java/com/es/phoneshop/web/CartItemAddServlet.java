package com.es.phoneshop.web;

import com.es.phoneshop.model.exception.ProductOutOfStockException;
import com.es.phoneshop.model.helpers.utils.AppConstants;
import com.es.phoneshop.model.helpers.utils.HttpSessionUtils;
import com.es.phoneshop.model.model.Cart;
import com.es.phoneshop.model.services.CartService;
import com.es.phoneshop.model.services.DefaultCartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class CartItemAddServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int quantity = parseQuantity(request);
            Cart cart = HttpSessionUtils.getCartFromSession(request.getSession());
            Long productId = parseProductId(request);
            cartService.addItem(cart, productId, quantity);
        } catch (ParseException e) {
            response.sendRedirect(getRedirectPath(request, AppConstants.Messages.INVALID_QUANTITY_MESSAGE));
            return;
        } catch (ProductOutOfStockException e) {
            response.sendRedirect(getRedirectPath(request, e.getMessage()));
            return;
        }
        response.sendRedirect(getRedirectPath(request, AppConstants.Messages.EMPTY_MESSAGE));
    }

    private Long parseProductId(HttpServletRequest request) {
        return Long.parseLong(request.getPathInfo().substring(1));
    }

    private Integer parseQuantity(HttpServletRequest request) throws ParseException {
        int quantity = NumberFormat.getInstance(request.getLocale())
                .parse(request.getParameter(AppConstants.Parameters.QUANTITY_ATTRIBUTE)).intValue();
        if (quantity <= 0) {
            throw new ParseException(AppConstants.Messages.EMPTY_MESSAGE, 0);
        }
        return quantity;
    }

    private String getMessageParamIfAddToCartSuccess(String errorMessage) {
        return errorMessage.isEmpty() ? "?message=" + AppConstants.Messages.PRODUCT_ADD_SUCCESS_MESSAGE
                : "?error=" + errorMessage;
    }

    private String getRedirectPath(HttpServletRequest request, String errorMessage) {
        String previousUrl = request.getHeader("Referer");
        previousUrl = previousUrl == null ? request.getContextPath() + "/products" : previousUrl;
        String newUrl = previousUrl.contains("?") ? previousUrl.substring(0, previousUrl.indexOf('?')) : previousUrl;
        return newUrl + getMessageParamIfAddToCartSuccess(errorMessage);
    }
}
