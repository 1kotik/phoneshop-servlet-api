package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public class ArrayListProductDao implements ProductDao {
    private Long currentProductId;
    private ArrayList<Product> products;
    private static ArrayListProductDao instance;

    private ArrayListProductDao() {
        products = new ArrayList<>();
        currentProductId = 0L;
        Currency usd = Currency.getInstance("USD");

        products.add(new Product(++currentProductId, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        products.add(new Product(++currentProductId, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        products.add(new Product(++currentProductId, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        products.add(new Product(++currentProductId, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        products.add(new Product(++currentProductId, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        products.add(new Product(++currentProductId, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        products.add(new Product(++currentProductId, "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        products.add(new Product(++currentProductId, "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        products.add(new Product(++currentProductId, "nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        products.add(new Product(++currentProductId, "palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        products.add(new Product(++currentProductId, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        products.add(new Product(++currentProductId, "simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        products.add(new Product(++currentProductId, "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
    }

    public static ArrayListProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    @Override
    public synchronized Optional<Product> getProduct(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot search. Product ID is null");
        }

        return products.stream()
                .filter(product -> id.equals(product.getId()))
                .findFirst();
    }

    @Override
    public synchronized List<Product> findProducts() {
        return products;
    }

    @Override
    public synchronized void save(Product product) {
        Optional<Product> productToUpdate;
        if (isProductValidToSave(product)) {
            throw new IllegalArgumentException("Fill in all parameters correctly!");
        }

        productToUpdate = getProductToUpdate(product);
        if (productToUpdate.isPresent()) {
            products.set(products.indexOf(productToUpdate.get()), product);

        } else {
            product.setId(++currentProductId);
            products.add(product);
        }
    }

    @Override
    public synchronized void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot delete. Product ID is null");
        }

        products.removeIf(product -> id.equals(product.getId()));
    }

    private boolean isProductValidToSave(Product product) {
        return product.getDescription().isEmpty()
                || product.getPrice() == null
                || product.getPrice().compareTo(BigDecimal.ZERO) < 0
                || product.getStock() < 0
                || product.getCode().isEmpty()
                || product.getImageUrl().isEmpty();
    }

    private Optional<Product> getProductToUpdate(Product product) {
        if (product.getId() != null) {
            return products.stream()
                    .filter(listedProduct -> product.getId().equals(listedProduct.getId()))
                    .findFirst();
        }
        return Optional.empty();
    }
}
