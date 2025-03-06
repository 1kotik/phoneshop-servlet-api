<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="message" type="java.lang.String" scope="request"/>
<tags:master pageTitle="Product List">
<h1>Exception occurred!</h1>
<p>${message}</p>

<form method="get">
    <input type="submit" value="Return to main page"/>
</form>
</tags:master>