<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.model.Product" scope="request"/>
<jsp:useBean id="recentlyViewedProducts" type="java.util.List" scope="request"/>
<jsp:useBean id="reviews" type="java.util.List" scope="request"/>
<jsp:useBean id="errors" type="java.util.Map" scope="request"/>

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
        <tr>
            <td>Rating</td>
            <td>${String.format("%.2f", product.averageRating)}</td>
        </tr>
    </table>

    <form method="post" action="${pageContext.servletContext.contextPath}/cart/modify-cart/${product.id}">
        <p>Set quantity</p>
        <input class="quantity-input" name="quantity" value="1">
        <input type="hidden" name="_method" value="POST">
        <button>Add to cart</button>
    </form>

    <c:forEach var="review" items="${reviews}">
        <div class="review">
            <div class="review-header">
            <span class="review-author">
                ${review.customer.firstName} ${review.customer.lastName}
            </span>
                <span class="review-rating">
                Rating: ${review.rating}
            </span>
            </div>
            <div class="review-text">
                <p>${review.text}</p>
            </div>
        </div>
    </c:forEach>

    <h2>Add review</h2>
    <form method="post" action="${pageContext.servletContext.contextPath}/product-review/${product.id}">
        <table>
            <tags:customerDetailsInput errors="${errors}" parameterName="firstName"
                                       value="${param['firstName']}" label="First name"/>

            <tags:customerDetailsInput errors="${errors}" parameterName="lastName"
                                       value="${param['lastName']}" label="Last name"/>
            <tr>
                <td>Rating (1-5):</td>
                <td>
                    <select name="averageRating" required>
                        <option value="">Select rating</option>
                        <option value="1" ${param.rating == '1' ? 'selected' : ''}>1 ★</option>
                        <option value="2" ${param.rating == '2' ? 'selected' : ''}>2 ★★</option>
                        <option value="3" ${param.rating == '3' ? 'selected' : ''}>3 ★★★</option>
                        <option value="4" ${param.rating == '4' ? 'selected' : ''}>4 ★★★★</option>
                        <option value="5" ${param.rating == '5' ? 'selected' : ''}>5 ★★★★★</option>
                    </select>
                    <c:if test="${not empty errors['averageRating']}">
                        <span class="error">${errors['averageRating']}</span>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>Your review:</td>
                <td>
                    <textarea name="productReviewText" rows="4" cols="50">${param.text}</textarea>
                </td>
            </tr>
        </table>
        <p>
            <button>Submit</button>
        </p>
    </form>

    <c:if test="${not empty param.error}">
        ${param.error}
    </c:if>
    <tags:recentlyViewedProducts recentlyViewedProducts="${recentlyViewedProducts}"/>
</tags:master>
