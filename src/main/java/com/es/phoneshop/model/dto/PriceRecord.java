package com.es.phoneshop.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class PriceRecord {
    private LocalDate date;
    private BigDecimal price;
    private Currency currency;

    public PriceRecord(LocalDate date, BigDecimal price, Currency currency) {
        this.date = date;
        this.price = price;
        this.currency = currency;
    }

    public PriceRecord(PriceRecord priceRecord) {
        this.date = priceRecord.date;
        this.price = priceRecord.price;
        this.currency = priceRecord.currency;
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
}
