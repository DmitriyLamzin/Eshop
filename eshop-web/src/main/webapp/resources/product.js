var pathParams = {};
var subCategoryId;
var categoryId;

var url;

function getUrl(){
    pathParams['minPrice'] = $('#minPrice').val();
    pathParams['maxPrice'] = $('#maxPrice').val();
    pathParams['orderBy'] = $('input[name=radioName]:checked', '#orderBy').val();
    pathParams['pageSize'] = $('input[name=pageSize]:checked', '#pageSize').val();
    pathParams['page'] = $('#page').val();
    //pathParams['producer'] = "_";
    categoryId = $('#selectCategory').val();
    subCategoryId = $('#selectSubcategory').val();
    var url = '/rest/' + categoryId + '/subcategories/' + subCategoryId + '/products';
    if (!$.isEmptyObject(pathParams)){
        url += '?';
        for (var key in pathParams){
            url += key + '=' + pathParams[key] + '&';
        }
    }
    return url;
}


$(document).ready(function() {
    $('#addProductButton').on('click', function () {
        $('#createSubCategory').toggle();
        $('input[name=categoryId]').val($('#selectCategory').val());
        $('input[name=subcategoryId]').val($('#selectSubcategory').val());
    });

    $('#createProductButton').on('click' , function(){
        var catId = $("input[name='categoryId']").val();
        var subCatId = $("input[name='subcategoryId']").val();
        var url = getUrl();
        var createUrl = '/rest/' + catId + '/subcategories/' + subCatId + '/products';
        createSubCategory(createUrl, function() {
            loadAllSubCategories(url, catId, createProductList);
        })
    });

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
    })
});



function createCategoryList(data) {
    var $categoryList  = $('#selectCategory');
    var categories = data._embedded.categoryBasicDtoList;
    $categoryList.empty();

    console.log(data);
    console.log(data._embedded.categoryBasicDtoList[0]._links.self);


    $.each(categories, function(i, category) {
        var option = document.createElement('option');
        option.value = category.categoryId;
        option.appendChild(document.createTextNode(category.name));
        //option.onchange = loadAllSubCategories('/rest/' + category.categoryId + '/subcategories', category.categoryId, createSubCategoryList);
        $categoryList.append(option);
    });
    var categoryId = $categoryList.val();
    loadAllSubCategories('/rest/' + categoryId + '/subcategories', categoryId, createSubCategoryList);

    $categoryList.on("change", function (){
        $('createSubCategory').hide();
        var categoryId = $categoryList.val();
        loadAllSubCategories('/rest/' + categoryId + '/subcategories', categoryId, createSubCategoryList);
    });

}

function createSubCategoryList(data) {
    console.log(data);

    var $subcategoryList = $('#selectSubcategory');
    $subcategoryList.empty();
    var subcategories = data._embedded.subcategoryBasicDtoList;

    console.log(data);
    console.log(data._embedded.subcategoryBasicDtoList[0]._links.self);

    $.each(subcategories, function(i, subcategory) {
        var option = document.createElement('option');

        option.value = subcategory.subcategoryId;
        option.appendChild(document.createTextNode(subcategory.subcategoryName));
        $subcategoryList.append(option);
        loadAllSubCategories(getUrl(), categoryId, createProductList);

    });
    $subcategoryList.on("change", function(){
        $('.container').empty();
        categoryId = $('#selectCategory').val();
        subCategoryId = $('#selectSubcategory').val();
        url = getUrl();
        //var url = '/rest/' + categoryId + '/subcategories/' + subCategoryId + '/products';
        loadAllSubCategories(url, categoryId, createProductList);
    })
}

function createProductList(data) {
    var $productList = $('.container');
    $productList.empty();
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
        price.appendChild(document.createTextNode(localizedMessages['lbl.price'] + " " + product.price));
        var productEntity = document.createElement('p');
        productEntity.appendChild(document.createTextNode(localizedMessages['lbl.producer']+" " + product.producer));
        col.appendChild(header);
        col.appendChild(productEntity);
        col.appendChild(price);

        var deleteButton = document.createElement('button');
        deleteButton.onclick = function () {
            var categoryId = $('#selectCategory').val();
            var subCatId = $('#selectSubcategory').val();
            var url = product._links.self.href;
            if (confirm(localizedMessages['lbl.removing.acceptance']) == true) {
                deleteCategory(url, function () {
                    $productList.empty();
                    loadAllSubCategories(getUrl(), categoryId, createProductList);
                })
            }
        };
        deleteButton.appendChild(document.createTextNode(localizedMessages['lbl.delete']));


        col.appendChild(deleteButton);

        row.appendChild(col);
        $productList.append(row);


        $productList.append(document.createElement('br'));
    });

}

