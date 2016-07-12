/**
 * Created by Dmitriy on 02.03.2016.
 */

var categories;
var subcategories;
loadAllCategories(getCategories);

$(document).ready(function() {
    var $body = $('body');
    $body.on('click', '#addCategoryButton', function (event) {
        $('#categoryContainer').toggle();
    });
    $body.on('click', '#addSubCategoryButton', function (event) {
        $('#subCategoryContainer').toggle();
        $('input[name=containCategoryId]').val($(this).attr('name'));
        var catalogContainer = document.getElementById("createSubCategoryButton");

        catalogContainer.value = $(this).attr('value');

        var url = catalogContainer.value;
        console.log(url);
    });
    $body.on('click', '#createSubCategoryButton', function (event) {
        event.preventDefault();
        var catId = $("input[name='containCategoryId']").val();
        var url = $('#createSubCategoryButton').val();
        console.log(url);
        createSubCategory(url, function (){
            $('#error_subCategoryId').empty();
            $('#error_subCategoryName').empty();
            $('#subcategoryContainer'+catId).remove();
            loadAllSubCategories(url, catId, getSubCategories)
        });
    });
    $body.on('click', 'a.subCategories', function (event) {
        event.preventDefault();
        var id = $(this).attr('id');
        if (!document.getElementById('subCatalog' + $(this).attr('id'))) {
            loadAllSubCategories($(this).attr('href'), $(this).attr('id'), getSubCategories);
        }
        else {
            $('#subCatalog' + id).remove();
        }
    }); // выводим полученные данные в консоль.
});// выводим полученные данные в консоль.

function getCategories(data){
    var catalogContainer = document.getElementById("catalog");
    categories = data._embedded.categoryBasicDtoList;

    console.log(data);
    console.log(data._embedded.categoryBasicDtoList[0]._links.self);
    if ($.isEmptyObject(categories)){
        var nothing = document.createTextNode("Нет ни одной категории");
        nothing.id = 'noCategories';
        var p = document.createElement('p');
        p.appendChild(document.createTextNode("Нет ни одной категории"));
        catalogContainer.appendChild(p);
    } else{
        if ($('#categoryList').length > 0)
            $('#categoryList').remove();

        $.each(categories, function(id, object){
            var li = document.createElement('li');
            li.class = "dropdown";
            li.id = 'li' + object.categoryId;
            var a = document.createElement('a');
            a.className = 'subCategories';
            a.href = object._links.subCategories.href;
            a.id = object.categoryId;
            a.appendChild(document.createTextNode(object.name + " "));
            li.appendChild(a);

            catalogContainer.appendChild(li);
        })
    }
}

function getSubCategories(data, categoryId){
    subcategories = data._embedded.subcategoryBasicDtoList;
    var id = categoryId;

    console.log(data);
    console.log(data._embedded.subcategoryBasicDtoList[0]._links.self);

    var catalogContainer = document.getElementById('li' + id);

    //var ul = document.createElement('ul');
    //ul.id = 'subCatalog' + id;
    //ul.class = "nav nav-sidebar";
    //catalogContainer.appendChild(ul);


        var ul = document.createElement('ul');
        //ul.id = 'subcategoryList' + id;
        ul.id = 'subCatalog' + id;
        ul.class = "nav";
        catalogContainer.appendChild(ul);
        $.each(subcategories, function(i, object){
            var li = document.createElement('li');
            li.className = "nav nav-sidebar";
            var a = document.createElement('a');
            a.className = 'getProductList';
            a.href = object._links.self.href;
            a.id = object.subcategoryName;
            a.appendChild(document.createTextNode(object.subcategoryName));
            li.appendChild(a);


            ul.appendChild(li);
        });
}



