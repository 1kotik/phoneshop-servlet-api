<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="priceHistory" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <h1>Price history</h1>
    <table>
        <thead>
        <tr>
            <td>Date</td>
            <td>Price</td>
        </tr>
        </thead>
        <c:forEach var="priceRecord" items="${priceHistory}">
            <tr>
                <td>${priceRecord.date}</td>
                <td class="price">
                    <fmt:formatNumber value="${priceRecord.price}" type="currency"
                                      currencySymbol="${priceRecord.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>