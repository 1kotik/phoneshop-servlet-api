package com.es.phoneshop.model.helpers.utils;

import java.util.Arrays;

public class ProductQueryUtils {
    private static String[] queryParts;

    public static void setQuery(String query){
        queryParts = query.trim().replaceAll(" +", " ").toLowerCase().split(" ");
    }

    public static long getQueryMatchNumber(String description) {
        return Arrays.stream(queryParts)
                .filter(queryPart -> description.toLowerCase().contains(queryPart))
                .count();
    }
}
