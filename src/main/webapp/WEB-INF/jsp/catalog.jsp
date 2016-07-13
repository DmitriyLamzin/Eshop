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
  <title>admin page</title>
  <spring:url value="/resources/jquery.min.js" var="jqueryJs" />
  <spring:url value="/resources/app.js" var="appJs" />
  <spring:url value="/resources/catalog.js" var="catalogJs" />
  <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
  <spring:url value="/resources/css/main.css" var="mainCss" />


  <script type="text/javascript" src="${jqueryJs}" ></script>
  <script type="text/javascript" src="${appJs}" ></script>
  <script type="text/javascript" src="${catalogJs}" ></script>
  <script type="text/javascript" src="${prodJs}" ></script>
  <script type="text/javascript" src="${bucketJs}" ></script>
  <link href="${bootstrapCss}" rel="stylesheet" />
  <link href="${mainCss}" rel="stylesheet" />

</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="/admin" >eShop admin tool</a>
    </div>
  </div>
</nav>
<div class="container-fluid">
  <div class="row">
    <div class="col-sm-3 col-md-2 sidebar">
      <ul id="admin_tools" class="nav nav-sidebar">
          <li>
            <a class="admin_tool" href="/admin/catalog">catalog sections manager</a>
          </li>
        <li>
          <a class="admin_tool" href="/admin/products">products manager  </a>
        </li>
        <li>
          <a class="admin_tool" href="/admin/order">orders manager</a>

        </li>
      </ul>
    </div>
    <div class="col-sm-9 col-md-10 main">
      <h1 class="page-header">Catalog section manager</h1>
      <button id="addCategoryButton">Добавить категорию</button><br><br>

      <div class="container">

      </div>

      <form class="form-group" id="createCategory" method="post" hidden>
        <div class="form-group">
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
        </div>
      </form>

      <form class="form-group" id="createSubCategory" method="post" hidden>
        <fieldset>
          Category Id:<br>
          <label>
            <input disabled type="text" name="containCategoryId" value="">
          </label>
          <legend>Create Subcategory:</legend>
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

  </div>
</div>
</body>
</html>
