package com.es.phoneshop.model.helpers.utils;

public final class AppConstants {
    private AppConstants() {}

    public static class JspFilePaths {
        private JspFilePaths() {}
        public static final String CART_JSP = "/WEB-INF/pages/cart.jsp";
        public static final String MINI_CART_JSP = "/WEB-INF/pages/miniCart.jsp";
        public static final String PRODUCT_DETAILS_JSP = "/WEB-INF/pages/productDetails.jsp";
        public static final String PRODUCT_LIST_JSP = "/WEB-INF/pages/productList.jsp";
        public static final String PRODUCT_PRICE_HISTORY_JSP = "/WEB-INF/pages/productPriceHistory.jsp";
    }

    public static class RequestAttributes {
        private RequestAttributes() {}
        public static final String PRODUCT_ATTRIBUTE = "product";
        public static final String PRODUCTS_ATTRIBUTE = "products";
        public static final String CART_ATTRIBUTE = "cart";
        public static final String ERRORS_ATTRIBUTE = "errors";
        public static final String QUANTITY_VALUES_ATTRIBUTE = "quantityValues";
        public static final String RECENTLY_VIEWED_PRODUCTS_ATTRIBUTE = "recentlyViewedProducts";
        public static final String PRICE_HISTORY_ATTRIBUTE = "priceHistory";
    }

    public static class Parameters {
        private Parameters() {}
        public static final String QUANTITY_ATTRIBUTE = "quantity";
        public static final String SORT_CRITERIA_PARAMETER = "sortCriteria";
        public static final String ORDER_PARAMETER = "order";
        public static final String QUERY_PARAMETER = "query";
        public static final String PRODUCT_ID_PARAMETER = "productId";
        public static final String PRODUCT_DEMO_DATA_ENABLED = "productDemoDataEnabled";
    }

    public static class Messages {
        private Messages() {}
        public static final String ITEM_DELETE_SUCCESS_MESSAGE = "Item is deleted successfully!";
        public static final String CART_UPDATE_SUCCESS_MESSAGE = "Cart is updated successfully!";
        public static final String CART_UPDATE_ERROR_MESSAGE = "Error occurred while updating cart";
        public static final String INVALID_QUANTITY_MESSAGE = "Invalid quantity";
        public static final String PRODUCT_ADD_SUCCESS_MESSAGE = "Product is added to the cart!";
        public static final String EMPTY_MESSAGE = "";
    }
}
