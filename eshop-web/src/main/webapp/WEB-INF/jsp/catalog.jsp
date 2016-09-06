  <%--
  Created by IntelliJ IDEA.
  User: Dmitriy
  Date: 02.03.2016
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

  <html>
<head>
  <title><spring:message code="lbl.admin.page" /></title>
  <spring:url value="/resources/jquery.min.js" var="jqueryJs" />
  <spring:url value="/resources/app.js" var="appJs" />
  <spring:url value="/resources/catalog.js" var="catalogJs" />
  <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
  <spring:url value="/resources/css/main.css" var="mainCss" />

    <script type="text/javascript">
        var localizedMessages = [];
        localizedMessages['lbl.delete'] = "<spring:message code="lbl.delete" javaScriptEscape="true" />";
        localizedMessages['lbl.add.subcategory'] = "<spring:message code="lbl.add.subcategory" javaScriptEscape="true" />";
        localizedMessages['lbl.removing.acceptance'] = "<spring:message code="lbl.removing.acceptance" javaScriptEscape="true" />";
    </script>


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
      <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
              <security:authorize access="hasRole('ROLE_ADMIN')">
              <li>
                  <a href="/logout"><spring:message code="lbl.logout"/></a>
              </li>
              </security:authorize>
          </ul>
      </div>
  </div>
</nav>
<div class="container-fluid">
  <div class="row">
    <div class="col-sm-3 col-md-2 sidebar">
      <ul id="admin_tools" class="nav nav-sidebar">
          <li>
            <a class="admin_tool" href="/admin/catalog"><spring:message code="lbl.catalog.sections.manager" /></a>
          </li>
        <li>
          <a class="admin_tool" href="/admin/products"><spring:message code="lbl.product.manager" /></a>
        </li>
        <li>
          <a class="admin_tool" href="/admin/order"><spring:message code="lbl.order.manager" /></a>

        </li>
      </ul>
    </div>
    <div class="col-sm-9 col-md-10 main">
      <h1 class="page-header"><spring:message code="lbl.catalog.sections.manager" /></h1>
      <button id="addCategoryButton"><spring:message code="lbl.add.category" /></button><br><br>

      <div class="container">
          <ul id="categoryList">
              <c:forEach var="category" items="${categories}">
                  <li id="li${category.categoryId}">
                      <a id=${category.categoryId} class="subCategories" href=${category.getLink("subCategories").getHref()}>
                      ${category.name}
                      </a>
                      <button name="Delete" id="delete_category_${category.getCategoryId()}" onclick="new function(){
                        var url = '${category.getLink('self').getHref()}';
                        if (confirm('Do you really want to delete this category and all its subcategories?') == true) {
                        deleteCategory(url, function(){
                        loadAllCategories(getCategories);
                        })
                      } else {

                      }
                      }"><spring:message code="lbl.delete"/></button>
                      <button id="addSubCategoryButton" name="${category.categoryId}" value="${category.getLink('subCategories').getHref()}">
                          <spring:message code="lbl.add.subcategory" />
                      </button>

                  </li>
              </c:forEach>
          </ul>
      </div>

      <form class="form-group" id="createCategory" method="post" hidden>
        <div class="form-group">
          <fieldset>
            <legend><spring:message code="lbl.add.category" /></legend>
              <spring:message code="lbl.category.id" />:<br>
            <label>
              <input type="text" name="categoryId">
            </label>
            <div id="error_categoryId"></div>
            <br>
              <spring:message code="lbl.category.name" />:<br>
            <label>
              <input type="text" name="name">
            </label>
            <div id="error_name"></div>
            <br><br>
            <input id="createCategoryButton" type="button" value="<spring:message code="lbl.submit" />" onclick="createCategory(function(){
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
            <spring:message code="lbl.category.id" />:<br>
          <label>
            <input disabled type="text" name="containCategoryId" value="">
          </label>
          <legend><spring:message code="lbl.add.subcategory" /></legend>
            <spring:message code="lbl.subcategory.id" />:<br>
          <label>
            <input type="text" name="subCategoryId">
          </label>
          <div id="error_subCategoryId"></div>
          <br>
            <spring:message code="lbl.subcategory.name" />:<br>
          <label>
            <input type="text" name="subCategoryName">
          </label>
          <div id="error_subCategoryName"></div>
          <br><br>
          <button id="createSubCategoryButton" type="submit" ><spring:message code="lbl.submit" /></button>
        </fieldset>
      </form>
    </div>
  </div>
</div>
</body>
</html>
