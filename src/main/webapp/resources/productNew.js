
/**
 * Created by Dmitriy on 13.04.2016.
 */


var pathParams = {};
var subCategoryId;
var categoryId;
var card;
var productList = {};
var url;

function initPathParam(url){
    var searchObject = {};
    var queries = url.search.replace(/^\?/, '').split('&');
    for (var i = 0; i < queries.length; i++){
        var split = queries[i].split('=');
        var paramId = "#" + split[0];
        console.log(split[0]);
        console.log(split[1]);
        searchObject[split[0]] = split[1];
        $(paramId).val(split[1]);

        //searchObject[split[0]] = split[1];
    }
    console.log(searchObject['minPrice']);
    //$('#minPrice').val()
}

function getUrl(){

    pathParams['minPrice'] = $('#minPrice').val();
    pathParams['maxPrice'] = $('#maxPrice').val();
    pathParams['orderBy'] = $('input[name=radioName]:checked', '#orderBy').val();
    pathParams['pageSize'] = $('input[name=pageSize]:checked', '#pageSize').val();
    pathParams['page'] = $('#page').val();

    var localUrlVariable = url.pathname;
    console.log(localUrlVariable);

    if (!$.isEmptyObject(pathParams)){
        localUrlVariable += '?';
        for (var key in pathParams){
            localUrlVariable += key + '=' + pathParams[key] + '&';
        }
    }

    console.log(localUrlVariable);
    return localUrlVariable;
}


$(document).ready(function() {
    var $body = $('body');

    //loadAllCategories(createCategoryList);
    //$('#addProductButton').on('click', function (event) {
    //    $('#productCreateContainer').toggle();
    //    $('input[name=categoryId]').val($('#selectCategory').val());
    //    $('input[name=subcategoryId]').val($('#selectSubcategory').val());
    //});

    //$('#createProductButton').on('click' , function(){
    //    var catId = $("input[name='categoryId']").val();
    //    var subCatId = $("input[name='subcategoryId']").val();
    //    var url = getUrl();
    //    var createUrl = '/rest/' + catId + '/subcategories/' + subCatId + '/products';
    //    createSubCategory(createUrl, function() {
    //        loadAllSubCategories(url, catId, createProductList);
    //    })
    //});

    $('#minPrice').on('change', function(){
        loadAllSubCategories(getUrl(), categoryId, createProductList);
    });

    $('#maxPrice').on('change', function(){
        loadAllSubCategories(getUrl(), categoryId, createProductList);
    });

    $('#orderBy').on('change', function(){
        loadAllSubCategories(getUrl(), categoryId, createProductList);
    });

    $('#pageSize').on('change', function() {
        loadAllSubCategories(getUrl(), categoryId, createProductList);
    });
    $('#page').on('change', function() {
        loadAllSubCategories(getUrl(), categoryId, createProductList);
    });

    $body.on('click', 'a.getProductList', function (event) {
        event.preventDefault();
        url = document.getElementById($(this).attr('id'));
        initPathParam(url);
        $('#productFilter').show();
        loadAllSubCategories($(this).attr('href'), categoryId, createProductList);
    });
});





//function createCategoryList(data) {
//    var $categoryList  = $('#selectCategory');
//    var categories = data._embedded.categoryBasicDtoList;
//
//    console.log(data);
//    console.log(data._embedded.categoryBasicDtoList[0]._links.self);
//
//
//    $.each(categories, function(i, category) {
//        var option = document.createElement('option');
//        option.value = category.categoryId;
//        option.appendChild(document.createTextNode(category.name));
//        //option.onchange = loadAllSubCategories('/rest/' + category.categoryId + '/subcategories', category.categoryId, createSubCategoryList);
//        $categoryList.append(option);
//    });
//    var categoryId = $categoryList.val();
//    loadAllSubCategories('/rest/' + categoryId + '/subcategories', categoryId, createSubCategoryList);
//
//    $categoryList.on("change", function (){
//        var categoryId = $categoryList.val();
//        loadAllSubCategories('/rest/' + categoryId + '/subcategories', categoryId, createSubCategoryList);
//    });
//
//}



function createProductList(data) {
    var $productList = $('.container');
    console.log(data);

    productList = data._embedded.productBasicDtoList;

    console.log(data._embedded.productBasicDtoList[0]._links.self);


    $productList.empty();
    $.each(productList, function(i, product) {
        var row = document.createElement('div');
        row.className = 'row';
        var col = document.createElement('div');
        col.className = 'col-lg-7';
        var header = document.createElement('h3');
        header.appendChild(document.createTextNode(product.productId + " "+ product.name));

        var price = document.createElement('p');
        price.appendChild(document.createTextNode("price " + product.price));
        var productEntity = document.createElement('p');
        productEntity.appendChild(document.createTextNode("producer " + product.producer));
        col.appendChild(header);
        col.appendChild(productEntity);
        col.appendChild(price);

        var addToCardButton = document.createElement("button");
        addToCardButton.onclick = function () {
               addProductToCard(product.productId);
        };
        addToCardButton.appendChild(document.createTextNode("add to Bucket"));

        col.appendChild(addToCardButton);

        row.appendChild(col);
        $productList.append(row);

        //var deleteButton = document.createElement('button');
        //deleteButton.onclick = function () {
        //    var categoryId = $('#selectCategory').val();
        //    var subCatId = $('#selectSubcategory').val();
        //    var url = product._links.self.href;
        //    if (confirm("Do you really want to delete this product?") == true) {
        //        deleteCategory(url, function () {
        //            $productList.empty();
        //            loadAllSubCategories(getUrl(), categoryId, createProductList);
        //        })
        //    }
        //};
        //deleteButton.appendChild(document.createTextNode("Delete"));
        //$productList.append(deleteButton);
        //
        //$productList.append(document.createElement('br'));
    })
}

