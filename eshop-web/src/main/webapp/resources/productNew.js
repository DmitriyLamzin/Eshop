

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
        price.appendChild(document.createTextNode(localizedMessages['lbl.price'] + " " + product.price));
        var productEntity = document.createElement('p');
        productEntity.appendChild(document.createTextNode(localizedMessages['lbl.producer'] + " " + product.producer));
        col.appendChild(header);
        col.appendChild(productEntity);
        col.appendChild(price);

        var addToCardButton = document.createElement("button");
        addToCardButton.onclick = function () {
            addProductToCard(product._links.self.href);
        };
        addToCardButton.appendChild(document.createTextNode(localizedMessages['lbl.to.bucket']));

        col.appendChild(addToCardButton);

        row.appendChild(col);
        $productList.append(row);

    })
}

