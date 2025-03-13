package com.es.phoneshop.model.helpers.utils;

import java.util.Arrays;

public final class ProductQueryUtils {

    private ProductQueryUtils() {}

    public static long getQueryMatchNumber(String description, String[] queryParts) {
        return Arrays.stream(queryParts)
                .filter(queryPart -> description.toLowerCase().contains(queryPart))
                .count();
    }
}
