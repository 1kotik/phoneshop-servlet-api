package com.es.phoneshop.model.helpers.enums;

import java.util.Arrays;

public enum PaymentMethod {
    CASH("Cash"), CREDIT_CARD("Credit card");
    String method;

    PaymentMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public static PaymentMethod getPaymentMethod(String method) {
        return Arrays.stream(PaymentMethod.values())
                .filter(paymentMethod -> method.equals(paymentMethod.getMethod()))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
