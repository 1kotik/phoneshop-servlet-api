<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@tag trimDirectiveWhitespaces="true" %>

<%@attribute name="errors" type="java.util.Map" required="true" %>
<%@attribute name="parameterName" type="java.lang.String" required="true" %>
<%@attribute name="value" type="java.lang.String" required="true" %>
<%@attribute name="label" type="java.lang.String" required="true" %>

<c:set var="error" value="${errors.get(parameterName)}"/>
<tr>
    <td>${label}</td>
    <td>
        <input name="${parameterName}" value="${not empty error ? param.get(parameterName) : value}"/>
        <c:if test="${not empty error }">
            <p>${error}</p>
        </c:if>
    </td>
</tr>
