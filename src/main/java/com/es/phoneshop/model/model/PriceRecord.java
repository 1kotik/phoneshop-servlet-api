package com.es.phoneshop.model.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class PriceRecord implements Cloneable, Serializable {
    private LocalDate date;
    private BigDecimal price;
    private Currency currency;

    public PriceRecord(LocalDate date, BigDecimal price, Currency currency) {
        this.date = date;
        this.price = price;
        this.currency = currency;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public PriceRecord clone() {
        try {
            return (PriceRecord) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
