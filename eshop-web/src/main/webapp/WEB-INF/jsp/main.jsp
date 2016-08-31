<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>main page</title>
  <spring:url value="/resources/jquery.min.js" var="jqueryJs" />
  <spring:url value="/resources/app.js" var="appJs" />
  <spring:url value="/resources/catalogNew.js" var="catalogJs" />
  <spring:url value="/resources/productNew.js" var="prodJs" />
  <spring:url value="/resources/bucket.js" var="bucketJs" />
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
      <a class="navbar-brand" href="/main" >eShop</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav navbar-right">
        <li>
          <a href="/bucket">Корзина</a>
        </li>
      </ul>
    </div>

  </div>
</nav>
<div class="container-fluid">
  <div class="row">
    <div class="col-sm-3 col-md-2 sidebar">
      <ul id="catalog" class="nav nav-sidebar">
        <c:forEach var="category" items="${categories}">
          <li id="li${category.categoryId}">
            <a id=${category.categoryId} class="subCategories" href=${category.getLink("subCategories").getHref()}>${category.name}</a>
          </li>
        </c:forEach>
      </ul>
      <div id="productFilter"  hidden>
        <p><strong>диапазон цен</strong></p>
        <form>
          <p>
            <label>
              <input id="minPrice" type="number" value="0"> -
            </label>
            <label>
              <input id="maxPrice" type="number" value="100000">
            </label>
          </p>
        </form>
        <p><strong>Сортировать по</strong></p>
        <form id="orderBy">
          <label>
            <input type="radio" name="radioName" value="producer" /> producer <br />
            <input type="radio" name="radioName" value="price" /> price <br />
            <input type="radio" name="radioName" value="id" checked/> id <br />
          </label>
        </form>

        <p><strong>Кол-во на странице</strong></p>
        <form id="pageSize">
          <label>
            <input type="radio" name="pageSize" value=5 /> 5 <br />
            <input type="radio" name="pageSize" value=10 /> 10 <br />
            <input type="radio" name="pageSize" value=20 checked/> 20 <br />
          </label>
        </form>

        <p><strong>Страница</strong></p>
        <form>
          <label>
            <input id="page" type="number" value="1">
          </label>
        </form>
      </div>
    </div>
    <div class="col-sm-9 col-md-10 main">
      <h1 class="page-header">Products</h1>

      <div class="container">
        <c:forEach var="product" items="${products}">
          <div class="row">
            <div class="col-lg-7">
              <h3>${product.productId} ${product.name}</h3>
              <p>producer ${product.producer}</p>
              <p>price ${product.price}</p>
              <button onclick="addProductToCard('${product.getId().href}')">add To bucket</button>
            </div>
          </div>
        </c:forEach>
      </div>
    </div>

  </div>
</div>



</body>
</html>
