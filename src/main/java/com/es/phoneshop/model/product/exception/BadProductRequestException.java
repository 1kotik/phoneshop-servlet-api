package com.es.phoneshop.model.product.exception;

public class BadProductRequestException extends RuntimeException {
    public BadProductRequestException(String message) {
        super(message);
    }
}
