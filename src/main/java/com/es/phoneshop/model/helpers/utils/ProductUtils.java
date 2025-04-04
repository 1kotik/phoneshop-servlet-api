package com.es.phoneshop.model.helpers.utils;

import com.es.phoneshop.model.model.PriceRecord;
import com.es.phoneshop.model.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public final class ProductUtils {
    private static String imageDirectory
            = "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/";

    private ProductUtils() {
    }

    public static List<Product> getSampleProducts() {
        List<Product> products = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        List<PriceRecord> priceHistory = new ArrayList<>();

        priceHistory.add(new PriceRecord(LocalDate.of(2020, 10, 12),
                new BigDecimal(100), usd));
        priceHistory.add(new PriceRecord(LocalDate.of(2021, 11, 12),
                new BigDecimal(200), usd));
        priceHistory.add(new PriceRecord(LocalDate.of(2022, 12, 12),
                new BigDecimal(300), usd));

        products.add(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100,
                imageDirectory + "Samsung/Samsung%20Galaxy%20S.jpg", priceHistory));
        products.add(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0,
                imageDirectory + "Samsung/Samsung%20Galaxy%20S%20II.jpg", priceHistory));
        products.add(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5,
                imageDirectory + "Samsung/Samsung%20Galaxy%20S%20III.jpg", priceHistory));
        products.add(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10,
                imageDirectory + "Apple/Apple%20iPhone.jpg", priceHistory));
        products.add(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30,
                imageDirectory + "Apple/Apple%20iPhone%206.jpg", priceHistory));
        products.add(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3,
                imageDirectory + "HTC/HTC%20EVO%20Shift%204G.jpg", priceHistory));
        products.add(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30,
                imageDirectory + "Sony/Sony%20Ericsson%20C901.jpg", priceHistory));
        products.add(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100,
                imageDirectory + "Sony/Sony%20Xperia%20XZ.jpg", priceHistory));
        products.add(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100,
                imageDirectory + "Nokia/Nokia%203310.jpg", priceHistory));
        products.add(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30,
                imageDirectory + "Palm/Palm%20Pixi.jpg", priceHistory));
        products.add(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20,
                imageDirectory + "Siemens/Siemens%20C56.jpg", priceHistory));
        products.add(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30,
                imageDirectory + "Siemens/Siemens%20C61.jpg", priceHistory));
        products.add(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40,
                imageDirectory + "Siemens/Siemens%20SXG75.jpg", priceHistory));

        return products;
    }
}
