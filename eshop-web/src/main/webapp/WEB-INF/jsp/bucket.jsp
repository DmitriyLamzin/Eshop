<%--
  Created by IntelliJ IDEA.
  User: Dmitriy
  Date: 29.06.2016
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
  <title>main page</title>
  <spring:url value="/resources/jquery.min.js" var="jqueryJs" />
  <spring:url value="/resources/app.js" var="appJs" />
  <spring:url value="/resources/catalog.js" var="catalogJs" />
  <%--<spring:url value="/resources/productNew.js" var="prodJs" />--%>
  <spring:url value="/resources/bucket.js" var="bucketJs" />
  <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
  <spring:url value="/resources/css/main.css" var="mainCss" />
  <script type="text/javascript">
    var localizedMessages = [];
    localizedMessages['lbl.from.bucket'] = "<spring:message code="lbl.from.bucket" javaScriptEscape="true" />";
    localizedMessages['lbl.total.price'] = "<spring:message code="lbl.total.price" javaScriptEscape="true" />";
    localizedMessages['lbl.price'] = "<spring:message code="lbl.price" javaScriptEscape="true" />";
    localizedMessages['lbl.producer'] = "<spring:message code="lbl.producer" javaScriptEscape="true" />";
    localizedMessages['lbl.size'] = "<spring:message code="lbl.size" javaScriptEscape="true" />";
  </script>

  <script type="text/javascript" src="${jqueryJs}" ></script>
  <script type="text/javascript" src="${appJs}" ></script>
  <%--<script type="text/javascript" src="${catalogJs}" ></script>--%>
  <%--<script type="text/javascript" src="${prodJs}" ></script>--%>
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
          <a href="/bucket"><spring:message code="lbl.bucket"/></a>
        </li>
      </ul>
    </div>

  </div>
</nav>
<div class="container-fluid">
  <div class="row">
    <div class="col-sm-3 col-md-2 sidebar">
      <ul id="catalog" class="nav nav-sidebar">

      </ul>
    </div>
    <div class="col-sm-9 col-md-10 main">
      <h1 class="page-header"><spring:message code="lbl.bucket"/></h1>

      <div class="container">

      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-9 col-md-10 main">
      <h3 class="page-header"><spring:message code="lbl.user.data"/></h3>

      <form class="form-group">
        <div class="form-group">
          <div id="error_personEmail"></div>
          <input id="userEmail" class="form-control" placeholder="Email" type="text" />
        </div>
      </form>
    </div>
  </div>
  <button id="sendOrder" class="button"><spring:message code="lbl.send.order"/></button>
</div>
<script>createProductListForBucket()</script>
</body>
</html>
