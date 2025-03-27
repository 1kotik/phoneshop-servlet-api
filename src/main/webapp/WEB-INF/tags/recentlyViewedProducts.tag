<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@tag trimDirectiveWhitespaces="true" %>

<%@attribute name="recentlyViewedProducts" type="java.util.List" required="true" %>

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