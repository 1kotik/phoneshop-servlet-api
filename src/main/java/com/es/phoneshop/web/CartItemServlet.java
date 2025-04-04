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
import java.util.Map;
import java.util.stream.IntStream;

public class CartItemServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int quantity = parseQuantity(request.getParameter(AppConstants.Parameters.QUANTITY_PARAMETER), request);
            Cart cart = HttpSessionUtils.getCartFromSession(request.getSession());
            Long productId = parseProductIdFromPathInfo(request);
            cartService.addItem(cart, productId, quantity);
            response.sendRedirect(getRedirectPath(request, AppConstants.Messages.EMPTY_MESSAGE));
        } catch (ParseException e) {
            response.sendRedirect(getRedirectPath(request, AppConstants.Messages.INVALID_QUANTITY_MESSAGE));
        } catch (ProductOutOfStockException e) {
            response.sendRedirect(getRedirectPath(request, e.getMessage()));
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] productIds = request.getParameterValues(AppConstants.Parameters.PRODUCT_ID_PARAMETER);
        String[] quantities = request.getParameterValues(AppConstants.Parameters.QUANTITY_PARAMETER);
        Map<String, String> errorMessages = HttpSessionUtils.getErrorMessagesFromSession(request.getSession());

        IntStream.range(0, productIds.length).forEach(i -> updateCartItem(request, productIds[i], quantities[i]));

        response.sendRedirect(request.getContextPath() + "/cart?message="
                + (errorMessages.isEmpty() ? AppConstants.Messages.CART_UPDATE_SUCCESS_MESSAGE
                : AppConstants.Messages.CART_UPDATE_ERROR_MESSAGE));
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long productId = parseProductIdFromPathInfo(request);
        cartService.deleteItem(HttpSessionUtils.getCartFromSession(request.getSession()), productId);
        response.sendRedirect(request.getContextPath() + "/cart?message=" +
                AppConstants.Messages.ITEM_DELETE_SUCCESS_MESSAGE);
    }

    private Long parseProductIdFromPathInfo(HttpServletRequest request) {
        return Long.parseLong(request.getPathInfo().substring(1));
    }

    private Integer parseQuantity(String stringQuantity, HttpServletRequest request) throws ParseException {
        int quantity = NumberFormat.getInstance(request.getLocale())
                .parse(stringQuantity).intValue();
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

    private void updateCartItem(HttpServletRequest request, String stringProductId, String stringQuantity) {
        Cart cart = HttpSessionUtils.getCartFromSession(request.getSession());
        Map<String, String> errorMessages = HttpSessionUtils.getErrorMessagesFromSession(request.getSession());
        Map<Long, String> quantityValues = HttpSessionUtils.getQuantityValuesFromSession(request.getSession());
        Long productId = Long.parseLong(stringProductId);

        quantityValues.put(productId, stringQuantity);

        try {
            int quantity = parseQuantity(stringQuantity, request);
            cartService.updateItem(cart, productId, quantity);
        } catch (ParseException e) {
            errorMessages.put(productId.toString(), AppConstants.Messages.INVALID_QUANTITY_MESSAGE);
        } catch (ProductOutOfStockException e) {
            errorMessages.put(productId.toString(), e.getMessage());
        }
    }

}
