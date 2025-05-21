package com.es.phoneshop.model.helpers.utils;

public final class AppConstants {
    private AppConstants() {
    }

    public static class JspFilePaths {
        private JspFilePaths() {
        }

        public static final String CART_JSP = "/WEB-INF/pages/cart.jsp";
        public static final String MINI_CART_JSP = "/WEB-INF/pages/miniCart.jsp";
        public static final String PRODUCT_DETAILS_JSP = "/WEB-INF/pages/productDetails.jsp";
        public static final String PRODUCT_LIST_JSP = "/WEB-INF/pages/productList.jsp";
        public static final String PRODUCT_PRICE_HISTORY_JSP = "/WEB-INF/pages/productPriceHistory.jsp";
        public static final String CHECKOUT_JSP = "/WEB-INF/pages/checkout.jsp";
        public static final String OVERVIEW_JSP = "/WEB-INF/pages/overview.jsp";
        public static final String ADVANCED_SEARCH_JSP = "/WEB-INF/pages/advancedSearch.jsp";
    }

    public static class RequestAttributes {
        private RequestAttributes() {
        }

        public static final String PRODUCT_ATTRIBUTE = "product";
        public static final String PRODUCTS_ATTRIBUTE = "products";
        public static final String CART_ATTRIBUTE = "cart";
        public static final String ERRORS_ATTRIBUTE = "errors";
        public static final String QUANTITY_VALUES_ATTRIBUTE = "quantityValues";
        public static final String RECENTLY_VIEWED_PRODUCTS_ATTRIBUTE = "recentlyViewedProducts";
        public static final String PRICE_HISTORY_ATTRIBUTE = "priceHistory";
        public static final String ORDER_ATTRIBUTE = "order";
    }

    public static class Parameters {
        private Parameters() {
        }

        public static final String QUANTITY_PARAMETER = "quantity";
        public static final String SORT_CRITERIA_PARAMETER = "sortCriteria";
        public static final String ORDER_PARAMETER = "order";
        public static final String QUERY_PARAMETER = "query";
        public static final String PRODUCT_ID_PARAMETER = "productId";
        public static final String PRODUCT_DEMO_DATA_ENABLED = "productDemoDataEnabled";
        public static final String FIRST_NAME_PARAMETER = "firstName";
        public static final String LAST_NAME_PARAMETER = "lastName";
        public static final String PHONE_PARAMETER = "phone";
        public static final String DELIVERY_ADDRESS_PARAMETER = "deliveryAddress";
        public static final String DELIVERY_DATE_PARAMETER = "deliveryDate";
        public static final String PAYMENT_METHOD_PARAMETER = "paymentMethod";
        public static final String SEARCH_METHOD_PARAMETER = "searchMethod";
        public static final String MIN_PRICE_PARAMETER = "minPrice";
        public static final String MAX_PRICE_PARAMETER = "maxPrice";
    }

    public static class Messages {
        private Messages() {
        }

        public static final String ITEM_DELETE_SUCCESS_MESSAGE = "Item is deleted successfully!";
        public static final String CART_UPDATE_SUCCESS_MESSAGE = "Cart is updated successfully!";
        public static final String CART_UPDATE_ERROR_MESSAGE = "Error occurred while updating cart";
        public static final String INVALID_QUANTITY_MESSAGE = "Invalid quantity";
        public static final String PRODUCT_ADD_SUCCESS_MESSAGE = "Product is added to the cart!";
        public static final String EMPTY_MESSAGE = "";
        public static final String INVALID_PARAMETER_MESSAGE = "Parameter should not be empty";
        public static final String INVALID_DATE_MESSAGE = "Date should be in the format DD.MM.YYYY";
        public static final String INVALID_PAYMENT_METHOD_MESSAGE = "Select a payment method";
        public static final String ORDER_SAVE_SUCCESS_MESSAGE = "Order is saved successfully";
        public static final String ORDER_SAVE_ERROR_MESSAGE = "Error occurred while saving order";
        public static final String NOT_A_NUMBER_ERROR_MESSAGE = "Not a number";
    }
}
