<%--
  Created by IntelliJ IDEA.
  User: Dmitriy
  Date: 18/02/2016
  Time: 12:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>admin page, limited access</title>
    <spring:url value="/resources/jquery.min.js" var="jqueryJs" />
    <spring:url value="/resources/app.js" var="appJs" />

    <script type="text/javascript" src="${jqueryJs}" ></script>
    <script type="text/javascript" src="${appJs}" ></script>

</head>
<body>
<p> Hello, Admin</p>
<ul id="navbar">
    <li><a class="ajax" href="${pageContext.request.contextPath}/admin">Главная</a></li>
    <li><a class="ajax" href="${pageContext.request.contextPath}/admin/catalog">Управление каталогом</a></li>
    <li><a class="ajax" href="${pageContext.request.contextPath}/admin/products">Управление продуктами</a></li>
</ul>
<div class="container" id="div0">
    <div class="container" id="div1">
        <h1>This is the main page</h1>
    </div>
</div>
</body>
</html>
