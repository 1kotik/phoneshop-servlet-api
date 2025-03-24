package com.es.phoneshop.model.helpers.utils;

import com.es.phoneshop.model.model.Cart;
import com.es.phoneshop.model.model.Product;
import jakarta.servlet.http.HttpSession;

import java.util.LinkedList;

public final class HttpSessionUtils {
    public static final String CART_SESSION_ATTRIBUTE = Cart.class.getName() + ".cart";
    public static final String RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE = Product.class.getName() + ".list";

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
}
