<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product List">
<h1>Product Info</h1>
<img class="product-tile" src="${product.imageUrl}"><br>

<p>ID: ${product.id} </p>

<form method="post">

    <label for="product-code">Code: </label>
    <input type="text" id="product-code" name="product-code" value="${product.code}"/><br>

    <label for="product-description">Model: </label>
    <input type="text" id="product-description" name="product-description" value="${product.description}"/><br>

    <label for="product-price">Price: </label>
    <input type="text" id="product-price" name="product-price" value="${product.price}"/>
    <input type="text" id="product-currency" name="product-currency" value="${product.currency.currencyCode}"/><br>

    <label for="product-stock">Stock: </label>
    <input type="text" id="product-stock" name="product-stock" value="${product.stock}"/><br>

    <label for="product-image-url">Image URL: </label>
    <input type="text" id="product-image-url" name="product-image-url" value="${product.imageUrl}"/><br>

    <input type="hidden" name="save-action" value="update"/>
    <input type="hidden" name="product-id" value="${product.id}"/>
    <input type="submit" value="UPDATE"/>

</form>

<form method="get">
    <input type="hidden" name="action" value="delete"/>
    <input type="hidden" name="product-id" value="${product.id}"/>
    <input type="submit" value="DELETE"/>
</form>
</tags:master>





