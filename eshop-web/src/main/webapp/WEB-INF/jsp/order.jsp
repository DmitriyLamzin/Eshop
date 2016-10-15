<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false" %>

<html>
<head>
  <title><spring:message code="lbl.admin.page" /></title>
  <spring:url value="/resources/jquery.min.js" var="jqueryJs" />
  <spring:url value="/resources/app.js" var="appJs" />
  <spring:url value="/resources/catalog.js" var="catalogJs" />
  <spring:url value="/resources/product.js" var="prodJs" />
  <spring:url value="/resources/order.js" var="orderJs" />
  <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
  <spring:url value="/resources/css/main.css" var="mainCss" />

  <script type="text/javascript">
    var localizedMessages = [];
    localizedMessages['lbl.delete'] = "<spring:message code="lbl.delete" javaScriptEscape="true" />";
    localizedMessages['lbl.add.product'] = "<spring:message code="lbl.add.product" javaScriptEscape="true" />";
    localizedMessages['lbl.price'] = "<spring:message code="lbl.price" javaScriptEscape="true" />";
    localizedMessages['lbl.producer'] = "<spring:message code="lbl.producer" javaScriptEscape="true" />";
    localizedMessages['lbl.removing.acceptance'] = "<spring:message code="lbl.removing.acceptance" javaScriptEscape="true" />";
    localizedMessages['lbl.total.price'] = "<spring:message code="lbl.total.price" javaScriptEscape="true" />";
  </script>

  <script type="text/javascript" src="${jqueryJs}" ></script>
  <script type="text/javascript" src="${appJs}" ></script>
  <%--<script type="text/javascript" src="${catalogJs}" ></script>--%>
  <script type="text/javascript" src="${prodJs}" ></script>
  <script type="text/javascript" src="${orderJs}" ></script>
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
      <h1 class="page-header"><spring:message code="lbl.order.manager" /></h1>

      <div class="container">
        <c:forEach var="order" items="${orders}">
          <div class="row">
            <div class="col-lg-7">
              <h3>${order.orderCardId}</h3>
              <ul>
                <c:forEach var="orderItem" items="${order.orderItems}">
                  <li>
                    ${orderItem.product.productId} ${orderItem.product.name} ${orderItem.product.price}x${orderItem.size} = ${orderItem.getPrice()}
                  </li>
                </c:forEach>
              </ul>
              <spring:message code="lbl.total.price" /> = ${order.getTotalPrice()}
              <br>
              <button onclick="deleteCategory('${order.getId().href}', function(){
                      loadAllSubCategories(orderListRequestUri, '', createOrderList)})">
                <spring:message code="lbl.delete" />
              </button>
            </div>
          </div>
        </c:forEach>
      </div>
    </div>
  </div>
</div>
</body>
</html>