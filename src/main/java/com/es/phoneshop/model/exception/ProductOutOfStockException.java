package com.es.phoneshop.model.exception;

public class ProductOutOfStockException extends RuntimeException {
    public ProductOutOfStockException(int stock, int quantity) {
        super("Quantity is out of stock. Available: "
                + stock + ". Desired quantity: " + quantity);
    }
}
