<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.es.phoneshop.model.helpers.enums.SearchMethod" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.List" scope="request"/>
<jsp:useBean id="errors" type="java.util.Map" scope="request"/>


<tags:master pageTitle="Product List">

    <c:if test="${not empty errors}">
        <p>Error occurred while searching</p>
    </c:if>
    <p>
        Advanced search
    </p>
    <form>
        <table>
            <tr>
                <td>Description</td>
                <td><input name="query" value="${param.query}"></td>
            </tr>
            <tr>
                <td>Min price</td>
                <td><input name="minPrice" value="${param.minPrice}">
                    <c:if test="${not empty errors['minPrice'] }">
                        <p>${errors['minPrice']}</p>
                    </c:if></td>
            </tr>
            <tr>
                <td>Max price</td>
                <td><input name="maxPrice" value="${param.maxPrice}">
                    <c:if test="${not empty errors['maxPrice'] }">
                        <p>${errors['maxPrice']}</p>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>Payment method</td>
                <td><select name="searchMethod">
                    <c:forEach var="searchMethod" items="${SearchMethod.values()}">
                        <option>${searchMethod.getMethod()}</option>
                    </c:forEach>
                </select>
                </td>
            </tr>
        </table>
        <p>
            <button>Search</button>
        </p>
    </form>
    <c:if test="${not empty products}">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description</td>
                <td>Quantity</td>
                <td class="price">Price</td>
            </tr>
            </thead>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>
                        <img class="product-tile" src="${product.imageUrl}">
                    </td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                                ${product.description}</a>
                    </td>
                    <form method="post"
                          action="${pageContext.servletContext.contextPath}/cart/modify-cart/${product.id}">
                        <td>
                            <input class="quantity-input" name="quantity" value="1">
                        </td>
                        <td class="price">
                            <a href="${pageContext.servletContext.contextPath}/products/price-history/${product.id}">
                                <fmt:formatNumber value="${product.price}" type="currency"
                                                  currencySymbol="${product.currency.symbol}"/>
                            </a>
                        </td>
                        <td>
                            <button>Add to cart</button>
                        </td>
                        <input type="hidden" name="_method" value="POST">
                    </form>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</tags:master>
