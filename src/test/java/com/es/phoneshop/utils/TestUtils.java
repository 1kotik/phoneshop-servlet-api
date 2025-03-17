package com.es.phoneshop.utils;

import com.es.phoneshop.model.dto.PriceRecord;
import com.es.phoneshop.model.dto.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class TestUtils {
    public static List<Product> getSampleProducts() {
        Currency usd = Currency.getInstance("USD");
        List<Product> samples = new ArrayList<>();
        List<PriceRecord> priceHistory=new ArrayList<>();

        priceHistory.add(new PriceRecord(LocalDate.of(2020, 10, 12), new BigDecimal(100), usd));
        priceHistory.add(new PriceRecord(LocalDate.of(2021, 11, 12), new BigDecimal(200), usd));
        priceHistory.add(new PriceRecord(LocalDate.of(2022, 12, 12), new BigDecimal(300), usd));
        samples.add(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", priceHistory));
        samples.add(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(0), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg", priceHistory));
        samples.add(new Product(3L, "sgs3", "", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg", priceHistory));
        samples.add(new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg", priceHistory));
        samples.add(new Product(5L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg", priceHistory));
        return samples;
    }

    public static Product getProduct(){
       return getSampleProducts().get(0);
    }
}
