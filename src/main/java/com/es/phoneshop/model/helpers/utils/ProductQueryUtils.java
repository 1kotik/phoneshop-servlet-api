package com.es.phoneshop.model.helpers.utils;

import com.es.phoneshop.model.helpers.enums.SearchMethod;

import java.math.BigDecimal;
import java.util.Arrays;

public final class ProductQueryUtils {

    private ProductQueryUtils() {
    }

    public static long getQueryMatchNumber(String description, String[] queryParts) {
        return Arrays.stream(queryParts)
                .filter(queryPart -> description.toLowerCase().contains(queryPart))
                .count();
    }

    public static boolean isSuitableForSearchMethod(String description, String[] queryParts,
                                                    SearchMethod searchMethod) {
        if (searchMethod.equals(SearchMethod.ALL_WORDS)) {
            return isDescriptionContainsAllWords(description, queryParts);
        }
        return isDescriptionContainsAnyWord(description, queryParts);
    }

    public static String[] getQueryParts(String query){
        return query.trim().replaceAll(" +", " ").toLowerCase().split(" ");
    }

    public static boolean isInPriceRange(BigDecimal price, BigDecimal minPrice, BigDecimal maxPrice) {
        return price.compareTo(minPrice) >= 0 && price.compareTo(maxPrice) <= 0;
    }

    private static boolean isDescriptionContainsAllWords(String description, String[] queryParts) {
        return Arrays.stream(queryParts)
                .allMatch(queryPart -> description.toLowerCase().contains(queryPart));
    }

    private static boolean isDescriptionContainsAnyWord(String description, String[] queryParts) {
        return Arrays.stream(queryParts)
                .anyMatch(queryPart -> description.toLowerCase().contains(queryPart));
    }
}
