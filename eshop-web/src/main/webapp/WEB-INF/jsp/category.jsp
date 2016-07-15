<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>admin page, limited access</title>
    <spring:url value="/resources/jquery.min.js" var="jqueryJs" />
    <spring:url value="/resources/app.js" var="appJs" />
    <spring:url value="/resources/catalog.js" var="catalogJs" />


    <script type="text/javascript" src="${jqueryJs}" ></script>
    <script type="text/javascript" src="${appJs}" ></script>
    <script type="text/javascript" src="${catalogJs}" ></script>


</head>
<body onload="loadAllCategories()">
    <p> Hello, Admin</p>
    <ul id="navbar">
        <li><a class="ajax" href="${pageContext.request.contextPath}/admin">Главная</a></li>
        <li><a class="ajax" href="${pageContext.request.contextPath}/admin/catalog">Управление каталогом</a></li>
        <li><a class="ajax" href="${pageContext.request.contextPath}/admin/products">Управление продуктами</a></li>

    </ul>
    <div class="container" id="div0">
    <div class="container" id="div1">
        <h1>This is the category page</h1>
        <form id="createCategory" action="" method="post">
            <fieldset>
                <legend>Create Category:</legend>
                Category Id:<br>
                <label>
                    <input type="text" name="categoryId">
                </label>
                <br>
                Category name:<br>
                <label>
                    <input type="text" name="name">
                </label>
                <br><br>
                <input id="create" type="button" value="submit" onclick="createCategory(function(){
                    $('#response').empty();
                    loadAllCategories(getCategories);
                })">
            </fieldset>
        </form>

        <div id="response">

        </div>
        </div>
    </div>
</body>