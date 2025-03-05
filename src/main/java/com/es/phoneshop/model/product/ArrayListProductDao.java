package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.exception.BadProductRequestException;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;

public class ArrayListProductDao implements ProductDao {
    private Long currentProductId;
    private ArrayList<Product> products;

    public ArrayListProductDao(ArrayList<Product> products) {
        if (products == null) {
            this.products = new ArrayList<>();
            currentProductId = 0L;
        } else {
            this.products = products;
            if (!products.isEmpty()) {
                currentProductId = products.stream()
                        .max(Comparator.comparing(Product::getId))
                        .get().getId();
            } else {
                currentProductId = 0L;
            }
        }
    }

    public ArrayListProductDao() {
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

    @Override
    public Product getProduct(Long id) {
        if (id == null) {
            throw new BadProductRequestException("Cannot search. Product ID is null");
        }

        return products.stream()
                .filter(product -> id.equals(product.getId()))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product is not found"));
    }

    @Override
    public List<Product> findProducts() {
        List<Product> result = products.stream()
                .filter(product -> BigDecimal.ZERO.compareTo(product.getPrice()) < 0)
                .filter(product -> product.getStock() > 0)
                .filter(product -> !product.getDescription().isEmpty())
                .toList();

        if (result.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }

        return result;
    }

    @Override
    public void save(Product product) {
        Product productToUpdate;

        if ((product.getId() != null)
                && (productToUpdate = products.stream()
                .filter(listedProduct -> product.getId().equals(listedProduct.getId()))
                .findFirst().orElse(null)) != null) {
            products.set(products.indexOf(productToUpdate), product);
        } else {
            product.setId(++currentProductId);
            products.add(product);
        }
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new BadProductRequestException("Cannot delete. Product ID is null");
        }

        boolean isRemoved = products.removeIf(product -> id.equals(product.getId()));

        if (!isRemoved) {
            throw new ProductNotFoundException("Product to delete is not found");
        }
    }
}
