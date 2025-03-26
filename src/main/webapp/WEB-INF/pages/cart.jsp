<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.model.Cart" scope="request"/>
<jsp:useBean id="errors" type="java.util.Map" scope="request"/>
<jsp:useBean id="quantityValues" type="java.util.Map" scope="request"/>

<tags:master pageTitle="">

    <c:if test="${not empty param.message}">
        <p>${param.message}</p>
    </c:if>

    <c:if test="${not empty cart.items}">
        <h1>Cart</h1>
        <form method="post">
            <table>
                <thead>
                <tr>
                    <td>Image</td>
                    <td>Product</td>
                    <td>Quantity</td>
                    <td>Price</td>
                </tr>
                </thead>
                <c:forEach var="item" items="${cart.items}" varStatus="status">
                    <tr>
                        <td>
                            <img class="product-tile" src="${item.product.imageUrl}">
                        </td>
                        <td>
                            <a href="${pageContext.servletContext.contextPath}/products/${item.product.id}">
                                    ${item.product.description}</a>
                        </td>
                        <td>
                            <c:set var="quantityValue" value="${quantityValues.get(item.product.id)}"/>
                            <c:set var="error" value="${errors.get(item.product.id)}"/>
                            <input class="quantity-input" name="quantity"
                                   value="${not empty quantityValue ? quantityValue : item.quantity}">
                            <input type="hidden" name="productId" value="${item.product.id}">
                            <c:if test="${not empty error}">
                                <p>${error}</p>
                            </c:if>
                        </td>
                        <td>
                            <fmt:formatNumber value="${item.product.price}" type="currency"
                                              currencySymbol="${item.product.currency.symbol}"/>
                        </td>
                        <td>
                            <button form="delete"
                                    formaction="${pageContext.servletContext.contextPath}/cart/delete-cart-item/${item.product.id}">
                                Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td></td>
                    <td></td>
                    <td class="quantity-input">${cart.totalQuantity}</td>
                    <td>
                        <fmt:formatNumber value="${cart.totalPrice}" type="currency"
                                          currencySymbol="${cart.items.get(0).product.currency.symbol}"/>
                    </td>
                </tr>
            </table>
            <p>
                <button>Update</button>
            </p>
        </form>
    </c:if>
    <form id="delete" method="post"></form>
</tags:master>
