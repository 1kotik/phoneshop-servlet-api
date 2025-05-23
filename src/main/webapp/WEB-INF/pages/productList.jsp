<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="recentlyViewedProducts" type="java.util.List" scope="request"/>

<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>

    <c:if test="${not empty param.error}">
        <p>${param.error}</p>
    </c:if>

    <c:if test="${not empty param.message}">
        <p>${param.message}</p>
    </c:if>

    <a href="${pageContext.servletContext.contextPath}/cart">
        <p>Go to cart</p>
    </a>

    <form id="searchForm">
        <input name="query" value="${param.query}">
        <button type="submit">Search</button>
    </form>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description
                <tags:sortQuery sortCriteria="description" order="asc"/>
                <tags:sortQuery sortCriteria="description" order="desc"/>
            </td>
            <td>Quantity</td>
            <td class="price">Price
                <tags:sortQuery sortCriteria="price" order="asc"/>
                <tags:sortQuery sortCriteria="price" order="desc"/>
            </td>
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
                <form method="post" action="${pageContext.servletContext.contextPath}/cart/modify-cart/${product.id}">
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
    <tags:recentlyViewedProducts recentlyViewedProducts="${recentlyViewedProducts}"/>
</tags:master>
