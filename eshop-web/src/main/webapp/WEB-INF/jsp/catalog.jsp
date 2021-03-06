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
  <%@ page isELIgnored="false" %>


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
<%@include file="header.jsp"%>
<div class="container-fluid">
  <div class="row">
      <div class="col-sm-3 col-md-2 sidebar">
          <%@include file="adminMenu.jsp"%>
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
