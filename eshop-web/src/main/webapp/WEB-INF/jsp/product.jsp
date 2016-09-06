<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title><spring:message code="lbl.admin.page" /></title>
    <spring:url value="/resources/jquery.min.js" var="jqueryJs" />
    <spring:url value="/resources/app.js" var="appJs" />
    <spring:url value="/resources/catalog.js" var="catalogJs" />
    <spring:url value="/resources/product.js" var="prodJs" />
    <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
    <spring:url value="/resources/css/main.css" var="mainCss" />

    <script type="text/javascript">
        var localizedMessages = [];
        localizedMessages['lbl.delete'] = "<spring:message code="lbl.delete" javaScriptEscape="true" />";
        localizedMessages['lbl.add.product'] = "<spring:message code="lbl.add.product" javaScriptEscape="true" />";
        localizedMessages['lbl.price'] = "<spring:message code="lbl.price" javaScriptEscape="true" />";
        localizedMessages['lbl.producer'] = "<spring:message code="lbl.producer" javaScriptEscape="true" />";
        localizedMessages['lbl.removing.acceptance'] = "<spring:message code="lbl.removing.acceptance" javaScriptEscape="true" />";
    </script>

    <script type="text/javascript" src="${jqueryJs}" ></script>
    <script type="text/javascript" src="${appJs}" ></script>
    <%--<script type="text/javascript" src="${catalogJs}" ></script>--%>
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
            </ul><br><br>
            <div id="productFilter">
                <p><strong><spring:message code="lbl.choose.category" /></strong></p>
                <form>
                    <p>
                        <label>
                            <select id="selectCategory" onchange="new function(){
                                $('#createSubCategory').hide();
                                var categoryId = $('#selectCategory').val();
                                loadAllSubCategories('/rest/' + categoryId + '/subcategories', categoryId, createSubCategoryList);
                            }">
                                <c:forEach var="category" items="${categories}">
                                    <option value="${category.categoryId}">${category.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </p>
                </form>

                <p><strong><spring:message code="lbl.choose.subcategory" /></strong></p>
                <form>
                    <p>
                        <label>
                            <select id="selectSubcategory" onchange="new function(){
                                        $('.container').empty();
                                        var categoryId = $('#selectCategory').val();
                                        var subCategoryId = $('#selectSubcategory').val();
                                        var url = getUrl();
                                        loadAllSubCategories(url, categoryId, createProductList);
                                            }">
                                <c:forEach var="subcategory" items="${subCategories}">
                                    <option value="${subcategory.subcategoryId}">${subcategory.subcategoryName}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </p>
                </form>

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
                        <input type="radio" name="radioName" value="price" /> <spring:message code="lbl.price" /> <br />
                        <input type="radio" name="radioName" value="id" checked/> <spring:message code="lbl.id" /> <br />
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
                        <input id="page" type="number" value="1">
                    </label>
                </form>
            </div>
        </div>
        <div class="col-sm-9 col-md-10 main">
            <h1 class="page-header"><spring:message code="lbl.product.manager" /></h1>
            <button id="addProductButton"><spring:message code="lbl.add.product" /></button>

            <div class="container">
                <c:forEach var="product" items="${products}">
                    <div class="row">
                        <div class="col-lg-7">
                            <h3>${product.productId} ${product.name}</h3>
                            <p><spring:message code="lbl.producer" /> ${product.producer}</p>
                            <p><spring:message code="lbl.price" /> ${product.price}</p>
                            <button onclick="deleteCategory('${product.getId().href}', function(){
                                    loadAllSubCategories(getUrl(), '', createProductList)})">
                                <spring:message code="lbl.delete" />
                            </button>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <form class="form-group" id="createSubCategory" method="post" hidden>
                <div class="form-group">
                    <fieldset>
                        <legend><spring:message code="lbl.add.product" />:</legend>
                        <spring:message code="lbl.category.id" />:<br>
                        <label>
                            <input disabled type="text" name="categoryId">
                        </label>
                        <div id="error_categoryId"></div>
                        <br>
                        <spring:message code="lbl.subcategory.id" />:<br>
                        <label>
                            <input disabled type="text" name="subcategoryId">
                        </label>
                        <%--<div id="error_name"></div>--%>
                        <br>
                        <spring:message code="lbl.product.id" />:<br>
                        <label>
                            <input type="text" name="id">
                        </label>
                        <%--<div id="error_name"></div>--%>
                        <br>

                        <spring:message code="lbl.product.name" />:<br>
                        <label>
                            <input type="text" name="name">
                        </label>
                        <%--<div id="error_name"></div>--%>
                        <br>

                        <spring:message code="lbl.price" />:<br>
                        <label>
                            <input type="text" name="price">
                        </label>
                        <%--<div id="error_name"></div>--%>
                        <br>

                        <spring:message code="lbl.producer" />:<br>
                        <label>
                            <input type="text" name="producer">
                        </label>
                        <%--<div id="error_name"></div>--%>
                        <br>

                        <br>
                        <input id="createProductButton" type="button" value="<spring:message code="lbl.submit" />">
                    </fieldset>
                </div>
            </form>

        </div>

    </div>
</div>
</body>
</html>