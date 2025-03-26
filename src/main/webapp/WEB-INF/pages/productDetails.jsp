<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.model.Product" scope="request"/>
<jsp:useBean id="recentlyViewedProducts" type="java.util.List" scope="request"/>

<tags:master pageTitle="Product List">

    <c:if test="${not empty param.error}">
        <p>Error occurred adding product</p>
    </c:if>
    <c:if test="${not empty param.message}">
        <p>${param.message}</p>
    </c:if>
    <a href="${pageContext.servletContext.contextPath}/cart">
        <p>Go to cart</p>
    </a>

    <h1>Product Info</h1>
    <img src="${product.imageUrl}">
    <table>
        <tr>
            <td>ID</td>
            <td>${product.id}</td>
        </tr>
        <tr>
            <td>Description</td>
            <td>${product.description}</td>
        </tr>
        <tr>
            <td>Price</td>
            <td><fmt:formatNumber value="${product.price}" type="currency"
                                  currencySymbol="${product.currency.symbol}"/></td>
        </tr>
        <tr>
            <td>Code</td>
            <td>${product.code}</td>
        </tr>
        <tr>
            <td>Stock</td>
            <td>${product.stock}</td>
        </tr>
    </table>

    <form method="post" action="${pageContext.servletContext.contextPath}/cart/add-cart-item/${product.id}">
        <p>Set quantity</p>
        <input class="quantity-input" name="quantity" value="1">
        <button>Add to cart</button>
    </form>
    <c:if test="${not empty param.error}">
        ${param.error}
    </c:if>
    <c:if test="${not empty recentlyViewedProducts}">
        <h2>Recently viewed products</h2>
        <div class="recently-viewed">
            <c:forEach var="viewedProduct" items="${recentlyViewedProducts}">
                <div class="recently-viewed-product">
                    <img src="${viewedProduct.imageUrl}">
                    <div><a href="${pageContext.servletContext.contextPath}/products/${viewedProduct.id}">
                            ${viewedProduct.description}</a></div>
                    <div><fmt:formatNumber value="${viewedProduct.price}" type="currency"
                                           currencySymbol="${viewedProduct.currency.symbol}"/></div>
                </div>
            </c:forEach>
        </div>
    </c:if>

</tags:master>
