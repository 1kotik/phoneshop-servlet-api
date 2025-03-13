package com.es.phoneshop.model.services;

import com.es.phoneshop.model.exception.ProductOutOfStockException;
import com.es.phoneshop.model.model.Cart;
import com.es.phoneshop.model.model.CartItem;
import com.es.phoneshop.model.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class DefaultCartService implements CartService {
    private static DefaultCartService cartService;
    private final ProductService productService;

    public static synchronized DefaultCartService getInstance() {
        if (cartService == null) {
            cartService = new DefaultCartService();
        }
        return cartService;
    }

    private DefaultCartService() {
        productService = DefaultProductService.getInstance();
    }

    public DefaultCartService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void addItem(Cart cart, Long productId, int quantity) {
        synchronized (cart) {
            getItemByProductId(cart, productId).ifPresentOrElse(item -> addItemIfAlreadyInCart(cart, item, quantity),
                    () -> addItemIfNotInCart(cart, productId, quantity));
            calculateCartTotals(cart);
        }
    }

    @Override
    public Optional<CartItem> getItemByProductId(Cart cart, Long productId) {
        synchronized (cart) {
            return cart.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst();
        }
    }

    @Override
    public void updateItem(Cart cart, Long productId, int quantity) {
        synchronized (cart) {
            getItemByProductId(cart, productId).ifPresentOrElse(item -> updateItemIfAlreadyInCart(cart, item, quantity),
                    () -> addItemIfNotInCart(cart, productId, quantity));
            calculateCartTotals(cart);
        }
    }

    @Override
    public void deleteItem(Cart cart, Long productId) {
        synchronized (cart) {
            cart.getItems().removeIf(item -> productId.equals(item.getProduct().getId()));
            calculateCartTotals(cart);
        }
    }

    private void addItemIfAlreadyInCart(Cart cart, CartItem item, int quantity) {
        if (item.getQuantity() + quantity <= item.getProduct().getStock()) {
            item.setQuantity(item.getQuantity() + quantity);
            setItem(cart, item);
        } else {
            throw new ProductOutOfStockException(item.getProduct().getStock(), item.getQuantity() + quantity);
        }
    }

    private void addItemIfNotInCart(Cart cart, Long productId, int quantity) {
        Product product = productService.getProduct(productId);
        if (quantity <= product.getStock()) {
            cart.getItems().add(new CartItem(product, quantity));
        } else {
            throw new ProductOutOfStockException(product.getStock(), quantity);
        }
    }

    public void setItem(Cart cart, CartItem item) {
        List<CartItem> items = cart.getItems();
        items.set(items.indexOf(item), item);
    }

    private void calculateCartTotals(Cart cart) {
        cart.setTotalQuantity(cart.getItems().stream()
                .mapToInt(CartItem::getQuantity).sum());
        cart.setTotalPrice(cart.getItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    private void updateItemIfAlreadyInCart(Cart cart, CartItem item, int quantity) {
        if (quantity <= item.getProduct().getStock()) {
            item.setQuantity(quantity);
            setItem(cart, item);
        } else {
            throw new ProductOutOfStockException(item.getProduct().getStock(), quantity);
        }
    }
}
