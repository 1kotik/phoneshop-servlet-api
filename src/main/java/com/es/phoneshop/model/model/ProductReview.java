package com.es.phoneshop.model.model;

import java.io.Serializable;

public class ProductReview implements Cloneable, Serializable {
    String text;
    int rating;
    Customer customer;

    public ProductReview() {
        customer = new Customer();
    }

    public ProductReview(String text, int rating, Customer customer) {
        this.text = text;
        this.rating = rating;
        this.customer = customer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public ProductReview clone() {
        ProductReview clone;
        try {
            clone = (ProductReview) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        clone.customer = this.customer.clone();
        return clone;
    }
}

