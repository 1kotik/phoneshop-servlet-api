package com.es.phoneshop.utils;

import com.es.phoneshop.model.helpers.enums.PaymentMethod;
import com.es.phoneshop.model.model.Cart;
import com.es.phoneshop.model.model.CartItem;
import com.es.phoneshop.model.model.Customer;
import com.es.phoneshop.model.model.Order;
import com.es.phoneshop.model.model.PriceRecord;
import com.es.phoneshop.model.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class TestUtils {
    private static final String imageDirectory
            = "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/";

    public static List<Product> getSampleProducts() {
        Currency usd = Currency.getInstance("USD");
        List<Product> samples = new ArrayList<>();
        List<PriceRecord> priceHistory = new ArrayList<>();

        priceHistory.add(new PriceRecord(LocalDate.of(2020, 10, 12),
                new BigDecimal(100), usd));
        priceHistory.add(new PriceRecord(LocalDate.of(2021, 11, 12),
                new BigDecimal(200), usd));
        priceHistory.add(new PriceRecord(LocalDate.of(2022, 12, 12),
                new BigDecimal(300), usd));
        samples.add(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd,
                100, imageDirectory + "Samsung/Samsung%20Galaxy%20S.jpg", priceHistory));
        samples.add(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(0), usd,
                10, imageDirectory + "Samsung/Samsung%20Galaxy%20S%20II.jpg", priceHistory));
        samples.add(new Product(3L, "sgs3", "", new BigDecimal(300), usd,
                5, imageDirectory + "Samsung/Samsung%20Galaxy%20S%20III.jpg", priceHistory));
        samples.add(new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd,
                10, imageDirectory + "Apple/Apple%20iPhone.jpg", priceHistory));
        samples.add(new Product(5L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd,
                30, imageDirectory + "Apple/Apple%20iPhone%206.jpg", priceHistory));
        return samples;
    }

    public static Product getProduct() {
        return getSampleProducts().get(0).clone();
    }

    public static Cart getSampleCart() {
        return new Cart(getSampleProducts().stream()
                .filter(product -> product.getStock() != 0)
                .map(product -> new CartItem(product, 2))
                .toList());
    }

    public static List<Order> getSampleOrders() {
        Customer customer1 = new Customer("John", "Smith", "+1111111111");
        Customer customer2 = new Customer("Jane", "Smith", "+2222222222");
        List<CartItem> items = getSampleCart().getItems();
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1L, items, "secure1", customer1, "addr1", LocalDate.now(),
                PaymentMethod.CASH));
        orders.add(new Order(2L, items, "secure2", customer2, "addr2", LocalDate.now(),
                PaymentMethod.CREDIT_CARD));

        return orders;
    }

}
