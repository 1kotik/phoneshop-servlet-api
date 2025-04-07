package com.es.phoneshop.model.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

public class Product extends GenericEntity implements Cloneable, Serializable {
    private String code;
    private String description;
    /**
     * null means there is no price because the product is outdated or new
     */
    private BigDecimal price;
    /**
     * can be null if the price is null
     */
    private Currency currency;
    private int stock;
    private String imageUrl;
    private List<PriceRecord> priceHistory;
    private double averageRating;

    public Product() {
    }

    public Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock,
                   String imageUrl, List<PriceRecord> priceHistory) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.priceHistory = priceHistory;
    }

    public Product(String code, String description, BigDecimal price, Currency currency, int stock,
                   String imageUrl, List<PriceRecord> priceHistory) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.priceHistory = priceHistory;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<PriceRecord> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<PriceRecord> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }

        Product product = (Product) object;

        return this.id.equals(product.getId())
                && this.code.equals(product.getCode())
                && this.description.equals(product.getDescription())
                && this.price.equals(product.getPrice())
                && this.currency.equals(product.getCurrency())
                && this.stock == product.getStock()
                && this.imageUrl.equals(product.getImageUrl());
    }

    public int hashCode() {
        return Objects.hash(id, code, description, price, currency, stock, imageUrl);
    }

    @Override
    public Product clone() {
        Product clone;
        clone = (Product) super.clone();
        clone.priceHistory = new ArrayList<>(priceHistory.stream()
                .map(PriceRecord::clone)
                .toList());
        return clone;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}
