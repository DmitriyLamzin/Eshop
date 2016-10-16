<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page isELIgnored="false" %>

<%--
  Created by IntelliJ IDEA.
  User: Dmitriy
  Date: 12.10.2016
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
