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
<body>
<p> Hello, Admin</p>
<ul id="navbar">
    <li><a class="ajax" href="${pageContext.request.contextPath}/admin">Главная</a></li>
    <li><a class="ajax" href="${pageContext.request.contextPath}/admin/catalog">Управление каталогом</a></li>
    <li><a class="ajax" href="${pageContext.request.contextPath}/admin/products">Управление продуктами</a></li>

</ul>
<div class="container" id="div0">

<div class="container" id="div1">
    <h1>This is the subcategory page</h1>
        <form id="createSubCategory" action="" method="post">
            <fieldset>
                Category Id:<br>
                <label>
                    <input type="text" name="categoryId">
                </label>
                <legend>Create Category:</legend>
                Subcategory Id:<br>
                <label>
                    <input type="text" name="subCategoryId">
                </label>
                <br>
                Subcategory name:<br>
                <label>
                    <input type="text" name="subCategoryName">
                </label>
                <br><br>
                <input id="create" type="button" value="submit">
            </fieldset>
        </form>

    </div>
    </div>
</body>
