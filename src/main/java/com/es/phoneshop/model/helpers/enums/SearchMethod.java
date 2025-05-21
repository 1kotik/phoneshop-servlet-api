package com.es.phoneshop.model.helpers.enums;

import java.util.Arrays;

public enum SearchMethod {
    ALL_WORDS("All words"), ANY_WORD("ANY_WORD");
    String method;

    SearchMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public static SearchMethod getSearchMethod(String method) {
        return Arrays.stream(SearchMethod.values())
                .filter(searchMethod -> method.equals(searchMethod.getMethod()))
                .findFirst().orElse(ANY_WORD);
    }
}
