function loadPage(url, callback){
    $("#div0").load(url + " #div1", callback); // выводим полученные данные в консоль.
}

function loadScript(){
    var s = document.createElement('script');
    s.type = 'text/javascript';
    s.async = true;
    if (window.history.state == "/admin/catalog"){
        s.src = '/resources/catalog.js';
    } else if (window.history.state == "/admin/products"){
        s.src = '/resources/product.js';
    }

    var x = document.getElementsByTagName('script')[0];
    x.parentNode.insertBefore(s, x);
}

function getEntity(url, callback){
    $.ajax({
        url: url, // url where to submit the request
        headers: {
            'Authorization':'Basic YWRtaW46cGFzcw==',
            'Content-Type':'application/hal+json'
            //'Accept' : 'application/hal+json'
        },
        type: "GET", // type of action POST || GET
        //dataType: 'json', // data type
        success: callback,
        error: function (xhr, resp, text) {
            console.log(xhr, resp, text, data.toString());
        }
    })
}

function sendEntity(url, entity, callback){
    $.ajax({
        url: url, // url where to submit the request
        headers: {
            'Authorization':'Basic YWRtaW46cGFzcw==',
            'Content-Type':'application/hal+json',
            'Accept' : 'application/hal+json'
        },
        type: "POST", // type of action POST || GET
        data: JSON.stringify(entity), // post data || get data
        success: callback,
        error: function (xhr, resp, text) {
            console.log(xhr, resp, text);
        }
    })
}

function loadAllCategories(callback){
    $.ajax({
        url: '/rest', // url where to submit the request
        headers: {
            'Authorization':'Basic YWRtaW46cGFzcw==',
                'Content-Type':'application/hal+json',
            'Accept' : 'application/hal+json'
        },
        type: "GET", // type of action POST || GET
        //dataType: 'json', // data type
        success: callback,
        error: function (xhr, resp, text) {
            console.log(xhr, resp, text, data.toString());
        }
    })
}

function loadAllSubCategories(url,categoryId, callback){
    $.ajax({
        url: url, // url where to submit the request
        headers: {
            'Authorization':'Basic YWRtaW46cGFzcw==',
            'Content-Type':'application/hal+json'
            //'Accept' : 'application/hal+json'
        },
        type: "GET", // type of action POST || GET
        //dataType: 'json', // data type
        success: function(data){
            callback(data, categoryId);
        },
        error: function (xhr, resp, text) {
            console.log(xhr, resp, text, data.toString());
        }
    })
}

window.onpopstate = function(){
    loadPage(document.location.href, loadScript);
};

function dataToJson(data){
    var jsonData ={};
    var formData = data;

    console.log(formData);

    $.each(formData, function() {
        if (jsonData[this.name]) {
            if (!jsonData[this.name].push) {
                jsonData[this.name] = [jsonData[this.name]];
            }
            jsonData[this.name].push(this.value || '');
        } else {
            jsonData[this.name] = this.value || '';
        }
    });
    return jsonData;
}
function createCategory(callback){
    var jsonData = dataToJson($('#createCategory').serializeArray());

    console.log(jsonData);
    // send ajax
    $.ajax({
        url: '/rest', // url where to submit the request
        headers: {
            'Authorization':'Basic YWRtaW46cGFzcw==',
            'Content-Type':'application/json'
        },
        type: "POST", // type of action POST || GET
        dataType: 'json', // data type
        data: JSON.stringify(jsonData), // post data || get data
        success: callback,
        error: function (xhr) {
            $('#error_categoryId').empty();
            $('#error_name').empty();
            console.log(xhr);
            var fieldErrorDTOs = xhr.responseJSON.fieldErrorDTOs;
            $.each(fieldErrorDTOs, function(i, object){
                var error = document.getElementById("error_" + object.field);
                error.appendChild(document.createTextNode(object.message));
                error.appendChild(document.createElement('br'));
            })
        }
    })
}

function deleteCategory(url, callback){
    var jsonData = dataToJson($('#createCategory').serializeArray());

    console.log(jsonData);
    // send ajax
    $.ajax({
        url: url, // url where to submit the request
        headers: {
            'Authorization':'Basic YWRtaW46cGFzcw==',
            'Content-Type':'application/hal+json'
        },
        type: "DELETE", // type of action POST || GET
        //dataType: 'json', // data type
        success: callback,
        error: function (xhr) {
            console.log(xhr);
            var fieldErrorDTOs = xhr.responseJSON.fieldErrorDTOs;
            var alertText ="";
            $.each(fieldErrorDTOs, function(i, object){
                alertText += object.message;
            });
            alert(alertText);
        }
    })
}

function createSubCategory(url, callback){
    var jsonData = dataToJson($('#createSubCategory').serializeArray());

    console.log(jsonData);
    // send ajax
    $.ajax({
        url: url, // url where to submit the request
        headers: {
            'Authorization':'Basic YWRtaW46cGFzcw==',
            'Content-Type':'application/hal+json'
        },
        type: "POST", // type of action POST || GET
        dataType: 'json', // data type
        data: JSON.stringify(jsonData), // post data || get data
        success: callback,
        error: function (xhr, resp, text) {
            $('#error_subCategoryId').empty();
            $('#error_subCategoryName').empty();
            console.log(xhr, resp, text);
            var fieldErrorDTOs = xhr.responseJSON.fieldErrorDTOs;
            $.each(fieldErrorDTOs, function(i, object) {
                var error = document.getElementById("error_" + object.field);
                error.appendChild(document.createTextNode(object.message));
                error.appendChild(document.createElement('br'));
            })
        }
    })
}

function createProduct(url, callback){
    var jsonData = dataToJson($('#createSubCategory').serializeArray());

    console.log(jsonData);
    // send ajax
    $.ajax({
        url: url, // url where to submit the request
        headers: {
            'Authorization':'Basic YWRtaW46cGFzcw==',
            'Content-Type':'application/hal+json'
        },
        type: "POST", // type of action POST || GET
        dataType: 'json', // data type
        data: JSON.stringify(jsonData), // post data || get data
        success: callback,
        error: function (xhr, resp, text) {
            $('#error_subCategoryId').empty();
            $('#error_subCategoryName').empty();
            console.log(xhr, resp, text);
            var fieldErrorDTOs = xhr.responseJSON.fieldErrorDTOs;
            $.each(fieldErrorDTOs, function(i, object) {
                var error = document.getElementById("error_" + object.field);
                error.appendChild(document.createTextNode(object.message));
                error.appendChild(document.createElement('br'));
            })
        }
    })
}

$(document).ready(function() {

    $('body').on('click', 'a.ajax', function (event) // вешаем обработчик на все ссылки, даже созданные после загрузки страницы
    {
        event.preventDefault(); // предотвращаем штатное действие, то есть переход по ссылке

        //$("#div1").load($(this).attr('href'), "#div1");
        window.history.pushState($(this).attr('href'),"Category", $(this).attr('href'));

        loadPage($(this).attr('href'), loadScript);// выводим полученные данные в консоль.

    }); // закрываем скобку :)

});