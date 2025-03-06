<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
<h1>Add product</h1>

<form method="post">

    <label for="product-code">Code: </label>
    <input type="text" id="product-code" name="product-code"/><br>

    <label for="product-description">Model: </label>
    <input type="text" id="product-description" name="product-description"/><br>

    <label for="product-price">Price: </label>
    <input type="text" id="product-price" name="product-price"/>
    <input type="text" id="product-currency" name="product-currency" placeholder="currency"/><br>

    <label for="product-stock">Stock: </label>
    <input type="text" id="product-stock" name="product-stock"/><br>

    <label for="product-image-url">Image URL: </label>
    <input type="text" id="product-image-url" name="product-image-url"/><br>

    <input type="hidden" name="save-action" value="add"/>
    <input type="submit" value="ADD"/>

</form>
</tags:master>