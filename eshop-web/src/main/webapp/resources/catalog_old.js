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
        if (!document.getElementById('subcategoryContainer' + $(this).attr('id'))) {
            loadAllSubCategories($(this).attr('href'), $(this).attr('id'), getSubCategories);
        }
        else {
            $('#subcategoryContainer' + id).remove();
        }
    }); // выводим полученные данные в консоль.
});// выводим полученные данные в консоль.

function getCategories(data){
    var catalogContainer = document.getElementById("div2");
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
        var ul = document.createElement('ul');
        ul.id = 'categoryList';
        catalogContainer.appendChild(ul);
        $.each(categories, function(id, object){
            var li = document.createElement('li');
            li.id = 'li' + object.categoryId;
            var a = document.createElement('a');
            a.className = 'subCategories';
            a.href = object._links.subCategories.href;
            a.id = object.categoryId;
            a.appendChild(document.createTextNode(object.name + " "));
            li.appendChild(a);

            var button = document.createElement('button');
            button.id = 'delete_category_' + object.categoryId;
            button.appendChild(document.createTextNode('Delete'));
            button.onclick = function(){
                var url = object._links.self.href;
                if (confirm("Do you really want to delete this category" +
                        "and all its subcategories?") == true) {
                    deleteCategory(url, function(){
                        li.remove();
                        loadAllCategories(getCategories);
                    })
                } else {

                }
            };
            button.name = 'Delete';
            li.appendChild(button);

            ul.appendChild(li);
        })
    }
}

function getSubCategories(data, categoryId){
    subcategories = data._embedded.subcategoryBasicDtoList;
    var id = categoryId;

    console.log(data);
    console.log(data._embedded.subcategoryBasicDtoList[0]._links.self);

    var catalogContainer = document.getElementById('li' + id);

    var div = document.createElement('div');
    div.id = 'subcategoryContainer' + id;
    catalogContainer.appendChild(div);

        var button = document.createElement('button');
        button.id = 'addSubCategoryButton';
        button.name = id;
        button.value = data._links.self.href;
        div.appendChild(button);
        button.appendChild(document.createTextNode('Добавить подкатегорию'));

        var ul = document.createElement('ul');
        ul.id = 'subcategoryList' + id;
        div.appendChild(ul);
        $.each(subcategories, function(i, object){
            var li = document.createElement('li');
            var a = document.createElement('a');
            a.class = 'subCategories';
            a.href = object._links.self.href;
            a.id = object.subcategoryName;
            a.appendChild(document.createTextNode(object.subcategoryName));
            li.appendChild(a);
            var button = document.createElement('button');
            button.id = 'delete_subcategory_' + object.subcategoryId;
            button.appendChild(document.createTextNode('Delete'));
            button.onclick = function(){
                var url = object._links.self.href;
                if (confirm("Do you really want to delete this subcategory?") == true) {
                    deleteCategory(url, function(){
                        div.remove();
                        var url = '/rest/' + id + '/subcategories';
                        loadAllSubCategories(url, id, getSubCategories);
                    })
                } else {

                }
            };
            button.name = 'Delete';
            li.appendChild(button);
            ul.appendChild(li);
        });
}



