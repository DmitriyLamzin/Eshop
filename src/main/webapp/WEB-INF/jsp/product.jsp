<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>admin page, limited access</title>
    <spring:url value="/resources/jquery.min.js" var="jqueryJs" />
    <spring:url value="/resources/app.js" var="appJs" />
    <spring:url value="/resources/product.js" var="prodJs" />

    <script type="text/javascript" src="${jqueryJs}" ></script>
    <script type="text/javascript" src="${appJs}" ></script>
    <script type="text/javascript" src="${prodJs}" ></script>

</head>
<body>
<p> Hello, Admin</p>
    <ul id="navbar">
        <li><a class="ajax" href="${pageContext.request.contextPath}/admin">Главная</a></li>
        <li><a class="ajax" href="${pageContext.request.contextPath}/admin/category">Управление категориями</a></li>
        <li><a class="ajax" href="${pageContext.request.contextPath}/admin/subcategory">Управление подкатегориями</a></li>
        <li><a class="ajax" href="${pageContext.request.contextPath}/admin/products">Управление продуктами</a></li>
    </ul>
<div class="container" id="div0">

    <div class="container" id="div1">
        <h1>This is the product page</h1>

        <button id="addProductButton">Добавить категорию</button>

        <p><strong>Выберите категорию</strong></p>
        <form>
            <p>
                <label>
                    <select id="selectCategory">
                    </select>
                </label>
            </p>
        </form>

        <p><strong>Выберите подкатегорию</strong></p>
        <form>
            <p>
                <label>
                    <select id="selectSubcategory">
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

        <div id="productList">
        </div>

        <div class="container" id="productCreateContainer" hidden>
            <form id="createSubCategory" action="" method="post">
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
            </form>
        </div>
    </div>
    </div>
</body>