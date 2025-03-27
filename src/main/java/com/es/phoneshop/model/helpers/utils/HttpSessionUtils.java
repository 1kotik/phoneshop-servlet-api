package com.es.phoneshop.model.helpers.utils;

import com.es.phoneshop.model.model.Cart;
import com.es.phoneshop.model.model.Product;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public final class HttpSessionUtils {
    public static final String CART_SESSION_ATTRIBUTE = Cart.class.getName() + ".cart";
    public static final String RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE = Product.class.getName() + ".list";
    public static final String ERROR_MESSAGES_SESSION_ATTRIBUTE = "ErrorMessages.map";
    public static final String QUANTITY_VALUES_SESSION_ATTRIBUTE = "QuantityValues.map";

    private HttpSessionUtils() {
    }

    public static Cart getCartFromSession(HttpSession httpSession) {
        Cart cart = (Cart) httpSession.getAttribute(CART_SESSION_ATTRIBUTE);
        if (cart == null) {
            cart = new Cart();
            httpSession.setAttribute(CART_SESSION_ATTRIBUTE, cart);
        }
        return cart;
    }

    public static LinkedList<Product> getRecentlyViewedProductsFromSession(HttpSession httpSession) {
        LinkedList<Product> products = (LinkedList<Product>) httpSession.getAttribute(RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE);
        if (products == null) {
            products = new LinkedList<>();
            httpSession.setAttribute(RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE, products);
        }
        return products;
    }

    public static Map<Long, String> getErrorMessagesFromSession(HttpSession httpSession) {
        Map<Long, String> errorMessages = (Map<Long, String>) httpSession.getAttribute(ERROR_MESSAGES_SESSION_ATTRIBUTE);
        if (errorMessages == null) {
            errorMessages = new HashMap<>();
            httpSession.setAttribute(ERROR_MESSAGES_SESSION_ATTRIBUTE, errorMessages);
        }
        return errorMessages;
    }

    public static Map<Long, String> getQuantityValuesFromSession(HttpSession httpSession) {
        Map<Long, String> quantityValues = (Map<Long, String>) httpSession.getAttribute(QUANTITY_VALUES_SESSION_ATTRIBUTE);
        if (quantityValues == null) {
            quantityValues = new HashMap<>();
            httpSession.setAttribute(QUANTITY_VALUES_SESSION_ATTRIBUTE, quantityValues);
        }
        return quantityValues;
    }
}
