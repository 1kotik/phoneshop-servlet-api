<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag trimDirectiveWhitespaces="true" %>
<%@attribute name="sortCriteria" required="true" %>
<%@attribute name="order" required="true" %>

<a href="?sortCriteria=${sortCriteria}&order=${order}
<c:if test="${not empty param.query}">&query=${fn:escapeXml(param.query)}</c:if>"
   style="${sortCriteria eq param.sortCriteria and order eq param.order ? 'font-weight: bold' : ''}">${order}</a>
