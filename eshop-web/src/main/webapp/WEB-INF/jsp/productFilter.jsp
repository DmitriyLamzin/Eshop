<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%--
  Created by IntelliJ IDEA.
  User: Dmitriy
  Date: 12.10.2016
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <p><strong><spring:message code="lbl.choose.price.range" /></strong></p>
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
  <p><strong><spring:message code="lbl.order.by" /></strong></p>
  <form id="orderBy">
    <label>
      <input type="radio" name="radioName" value="producer" /> <spring:message code="lbl.producer" /> <br />
      <input type="radio" name="radioName" value="price" checked/> <spring:message code="lbl.price" /> <br />
      <input type="radio" name="radioName" value="id" /> <spring:message code="lbl.id" /> <br />
    </label>
  </form>

  <p><strong><spring:message code="lbl.page.size" /></strong></p>
  <form id="pageSize">
    <label>
      <input type="radio" name="pageSize" value=5 /> 5 <br />
      <input type="radio" name="pageSize" value=10 /> 10 <br />
      <input type="radio" name="pageSize" value=20 checked/> 20 <br />
    </label>
  </form>

  <p><strong><spring:message code="lbl.page.number" /></strong></p>
  <form>
    <label>
      <input id="page" type="number" value="1" min="1" max="${pageNumbers}">
    </label>
  </form>
