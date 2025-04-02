package com.es.phoneshop.model.model;

import com.es.phoneshop.model.helpers.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Order extends Cart implements Cloneable {
    private String secureId;
    private Customer customer;
    private LocalDate deliveryDate;
    private String deliveryAddress;
    private PaymentMethod paymentMethod;
    private BigDecimal subtotal;
    private BigDecimal deliveryCost;

    public Order() {
        customer = new Customer();
    }

    public Order(Long id, List<CartItem> items, String secureId, Customer customer, String deliveryAddress,
                 LocalDate deliveryDate, PaymentMethod paymentMethod) {
        super(items);
        this.id = id;
        this.secureId = secureId;
        this.customer = customer.clone();
        this.deliveryDate = deliveryDate;
        this.deliveryAddress = deliveryAddress;
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getSecureId() {
        return secureId;
    }

    public void setSecureId(String secureId) {
        this.secureId = secureId;
    }

    @Override
    public Order clone() {
        Order clone;
        clone = (Order) super.clone();
        clone.customer = this.customer.clone();
        return clone;
    }
}
