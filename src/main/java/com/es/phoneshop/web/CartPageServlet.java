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

public class CartPageServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = HttpSessionUtils.getCartFromSession(request.getSession());
        Map<Long, String> errorMessages = HttpSessionUtils.getErrorMessagesFromSession(request.getSession());
        Map<Long, String> quantityValues = HttpSessionUtils.getQuantityValuesFromSession(request.getSession());

        request.setAttribute(AppConstants.RequestAttributes.CART_ATTRIBUTE, cart);
        request.setAttribute(AppConstants.RequestAttributes.ERRORS_ATTRIBUTE, errorMessages);
        request.setAttribute(AppConstants.RequestAttributes.QUANTITY_VALUES_ATTRIBUTE, quantityValues);
        request.getRequestDispatcher(AppConstants.JspFilePaths.CART_JSP).forward(request, response);

        errorMessages.clear();
        quantityValues.clear();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] productIds = request.getParameterValues(AppConstants.Parameters.PRODUCT_ID_PARAMETER);
        String[] quantities = request.getParameterValues(AppConstants.Parameters.QUANTITY_ATTRIBUTE);
        Map<Long, String> errorMessages = HttpSessionUtils.getErrorMessagesFromSession(request.getSession());

        IntStream.range(0, productIds.length).forEach(i -> updateCartItem(request, productIds[i], quantities[i]));

        response.sendRedirect(request.getContextPath() + "/cart?message="
                + (errorMessages.isEmpty() ? AppConstants.Messages.CART_UPDATE_SUCCESS_MESSAGE
                : AppConstants.Messages.CART_UPDATE_ERROR_MESSAGE));
    }

    private Long parseProductId(String productId) {
        return Long.parseLong(productId);
    }

    private Integer parseQuantity(String stringQuantity, HttpServletRequest request) throws ParseException {
        int quantity = NumberFormat.getInstance(request.getLocale())
                .parse(stringQuantity).intValue();
        if (quantity <= 0) {
            throw new ParseException(AppConstants.Messages.EMPTY_MESSAGE, 0);
        }
        return quantity;
    }

    private void updateCartItem(HttpServletRequest request, String stringProductId, String stringQuantity) {
        Cart cart = HttpSessionUtils.getCartFromSession(request.getSession());
        Map<Long, String> errorMessages = HttpSessionUtils.getErrorMessagesFromSession(request.getSession());
        Map<Long, String> quantityValues = HttpSessionUtils.getQuantityValuesFromSession(request.getSession());
        Long productId = parseProductId(stringProductId);

        quantityValues.put(productId, stringQuantity);

        try {
            int quantity = parseQuantity(stringQuantity, request);
            cartService.updateItem(cart, productId, quantity);
        } catch (ParseException e) {
            errorMessages.put(productId, AppConstants.Messages.INVALID_QUANTITY_MESSAGE);
        } catch (ProductOutOfStockException e) {
            errorMessages.put(productId, e.getMessage());
        }
    }
}
