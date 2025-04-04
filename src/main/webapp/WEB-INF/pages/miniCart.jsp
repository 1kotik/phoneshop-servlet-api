<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.model.Cart" scope="request"/>

<c:if test="${not empty cart.items}">
    Cart: ${cart.totalQuantity} items, total <fmt:formatNumber value="${cart.totalPrice}" type="currency"
                                                               currencySymbol="${cart.items.get(0)
                                                               .product.currency.symbol}"/>
</c:if>
