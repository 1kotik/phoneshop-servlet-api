<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.model.Order" scope="request"/>

<tags:master pageTitle="">

    <c:if test="${not empty param.message}">
        <p>${param.message}</p>
    </c:if>

    <c:if test="${not empty order.items}">
        <h1>Cart</h1>
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Product</td>
                <td>Quantity</td>
                <td>Price</td>
                <td>Delivery cost</td>
                <td>Total cost</td>
            </tr>
            </thead>
            <c:forEach var="item" items="${order.items}" varStatus="status">
                <tr>
                    <td>
                        <img class="product-tile" src="${item.product.imageUrl}">
                    </td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${item.product.id}">
                                ${item.product.description}</a>
                    </td>
                    <td>
                            ${item.quantity}
                    </td>
                    <td>
                        <fmt:formatNumber value="${item.product.price}" type="currency"
                                          currencySymbol="${item.product.currency.symbol}"/>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td></td>
                <td></td>
                <td class="quantity-input">${order.totalQuantity}</td>
                <c:set var="currency" value="${order.items.get(0).product.currency.symbol}"/>
                <td>
                    <fmt:formatNumber value="${order.subtotal}" type="currency"
                                      currencySymbol="${currency}"/>
                </td>
                <td>
                    <fmt:formatNumber value="${order.deliveryCost}" type="currency"
                                      currencySymbol="${currency}"/>
                </td>
                <td>
                    <fmt:formatNumber value="${order.totalPrice}" type="currency"
                                      currencySymbol="${currency}"/>
                </td>
            </tr>
        </table>
        <h2>Details</h2>
        <table>
            <tags:customerDetails value="${order.customer.firstName}" label="First name"/>
            <tags:customerDetails value="${order.customer.lastName}" label="Last name"/>
            <tags:customerDetails value="${order.customer.phone}" label="Phone"/>
            <tags:customerDetails value="${order.deliveryAddress}" label="Delivery address"/>
            <tags:customerDetails value="${order.deliveryDate.format(DateTimeFormatter.ofPattern('dd.MM.yyyy'))}"
                                  label="Delivery date"/>
            <tags:customerDetails value="${order.paymentMethod.method}" label="Payment method"/>
        </table>
    </c:if>
</tags:master>

