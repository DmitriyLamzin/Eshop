<%--
  Created by IntelliJ IDEA.
  User: Dmitriy
  Date: 12.10.2016
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false" %>

<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="/main" >eShop</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav navbar-right">
        <security:authorize access="hasRole('ROLE_ADMIN')">
          <li>
            <a href="/admin"><spring:message code="lbl.admin.page"/></a>
          </li>
          <li>
            <a href="/logout"><spring:message code="lbl.logout"/></a>
          </li>
        </security:authorize>
        <security:authorize access="hasRole('ROLE_ANONYMOUS')">
          <li>
            <a href="/login"><spring:message code="lbl.login"/></a>
          </li>
        </security:authorize>
        <li>
          <a href="/bucket"><spring:message code="lbl.bucket"/></a>
        </li>
      </ul>
    </div>

  </div>
</nav>