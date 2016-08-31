<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>admin page</title>
    <spring:url value="/resources/jquery.min.js" var="jqueryJs" />
    <spring:url value="/resources/app.js" var="appJs" />
    <spring:url value="/resources/catalog.js" var="catalogJs" />
    <spring:url value="/resources/product.js" var="prodJs" />
    <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
    <spring:url value="/resources/css/main.css" var="mainCss" />


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
            </ul><br><br>
            <div id="productFilter">
                <p><strong>Выберите категорию</strong></p>
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

                <p><strong>Выберите подкатегорию</strong></p>
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
            <h1 class="page-header">Product manager</h1>
            <button id="addProductButton">Добавить продукт</button>

            <div class="container">
                <c:forEach var="product" items="${products}">
                    <div class="row">
                        <div class="col-lg-7">
                            <h3>${product.productId} ${product.name}</h3>
                            <p>producer ${product.producer}</p>
                            <p>price ${product.price}</p>
                            <button onclick="deleteCategory('${product.getId().href}', function(){
                                    loadAllSubCategories(getUrl(), '', createProductList)})">
                                add To bucket
                            </button>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <form class="form-group" id="createSubCategory" method="post" hidden>
                <div class="form-group">
                    <fieldset>
                        <legend>Create Product:</legend>
                        Category Id:<br>
                        <label>
                            <input disabled type="text" name="categoryId">
                        </label>
                        <div id="error_categoryId"></div>
                        <br>
                        Subcategory Id:<br>
                        <label>
                            <input disabled type="text" name="subcategoryId">
                        </label>
                        <%--<div id="error_name"></div>--%>
                        <br>
                        Product Id:<br>
                        <label>
                            <input type="text" name="id">
                        </label>
                        <%--<div id="error_name"></div>--%>
                        <br>

                        Name:<br>
                        <label>
                            <input type="text" name="name">
                        </label>
                        <%--<div id="error_name"></div>--%>
                        <br>

                        Price:<br>
                        <label>
                            <input type="text" name="price">
                        </label>
                        <%--<div id="error_name"></div>--%>
                        <br>

                        Producer:<br>
                        <label>
                            <input type="text" name="producer">
                        </label>
                        <%--<div id="error_name"></div>--%>
                        <br>

                        <br>
                        <input id="createProductButton" type="button" value="submit">
                    </fieldset>
                </div>
            </form>

        </div>

    </div>
</div>
</body>
</html>