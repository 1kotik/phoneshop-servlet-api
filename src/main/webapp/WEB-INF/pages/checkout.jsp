<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.es.phoneshop.model.helpers.enums.PaymentMethod" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.model.Order" scope="request"/>
<jsp:useBean id="errors" type="java.util.Map" scope="request"/>


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
        <form method="post">
            <table>
                <tags:customerDetailsInput errors="${errors}" parameterName="firstName"
                                           value="${order.customer.firstName}" label="First name"/>

                <tags:customerDetailsInput errors="${errors}" parameterName="lastName"
                                           value="${order.customer.lastName}" label="Last name"/>

                <tags:customerDetailsInput errors="${errors}" parameterName="phone"
                                           value="${order.customer.phone}" label="Phone"/>

                <tags:customerDetailsInput errors="${errors}" parameterName="deliveryAddress"
                                           value="${order.deliveryAddress}" label="Delivery address"/>

                <tags:customerDetailsInput errors="${errors}" parameterName="deliveryDate"
                                           value="${order.deliveryDate
                                           .format(DateTimeFormatter.ofPattern('dd.MM.yyyy'))}"
                                           label="Delivery date"/>

                <tr>
                    <td>Payment method</td>
                    <td><select name="paymentMethod">
                        <c:forEach var="paymentMethod" items="${PaymentMethod.values()}">
                            <option>${paymentMethod.getMethod()}</option>
                        </c:forEach>
                    </select>
                    </td>
                </tr>
            </table>
            <p>
                <button>Place order</button>
            </p>
        </form>
    </c:if>
</tags:master>

