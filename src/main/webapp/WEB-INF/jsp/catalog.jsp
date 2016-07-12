<%--
  Created by IntelliJ IDEA.
  User: Dmitriy
  Date: 02.03.2016
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
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
      <div class="container" id="div2">
        <h1>This is the catalog control page</h1>
        Catalog structure<br>
        <button id="addCategoryButton">Добавить категорию</button>
      </div>

      <div class="container" id="subCategoryContainer" hidden>
        <h1>This is the subcategory page</h1>
        <form id="createSubCategory" action="" method="post">
          <fieldset>
            Category Id:<br>
            <label>
              <input disabled type="text" name="containCategoryId" value="">
            </label>
            <legend>Create Category:</legend>
            Subcategory Id:<br>
            <label>
              <input type="text" name="subCategoryId">
            </label>
            <div id="error_subCategoryId"></div>
            <br>
            Subcategory name:<br>
            <label>
              <input type="text" name="subCategoryName">
            </label>
            <div id="error_subCategoryName"></div>
            <br><br>
            <button id="createSubCategoryButton" type="submit" >submit</button>
          </fieldset>
        </form>
      </div>
      <div class="container" id="categoryContainer" hidden>
        <h1>This is the category page</h1>
        <form id="createCategory" action="" method="post">
          <fieldset>
            <legend>Create Category:</legend>
            Category Id:<br>
            <label>
              <input type="text" name="categoryId">
            </label>
            <div id="error_categoryId"></div>
            <br>
            Category name:<br>
            <label>
              <input type="text" name="name">
            </label>
            <div id="error_name"></div>
            <br><br>
            <input id="createCategoryButton" type="button" value="submit" onclick="createCategory(function(){
                    $('#error_categoryId').empty();
                    $('#error_name').empty();
                    $('#response').empty();
                    loadAllCategories(getCategories);
                })">
          </fieldset>
        </form>
      </div>
    </div>
  </div>
</body>
</html>
