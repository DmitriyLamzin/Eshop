/**
 * Created by Dmitriy on 29.06.2016.
 */
const getBucketUrl = '/rest/card';
const bucketName = 'userBucket';


$(document).ready(function() {
    var $body = $('body');

    $body.on('click', '#sendOrder', function (event) {
        var bucket = getBucket();
        bucket.personEmail = $('#userEmail').val();
        if (bucket.orderedProducts.length < 1){
            alert("there is no product in the bucket");
        } else{
            $.ajax({
                url: getBucketUrl, // url where to submit the request
                headers: {
                    'Authorization':'Basic YWRtaW46cGFzcw==',
                    'Content-Type':'application/hal+json',
                    'Accept' : 'application/hal+json'
                },
                type: "POST", // type of action POST || GET
                data: JSON.stringify(bucket), // post data || get data
                success: function(){
                    $('#error_personEmail').empty();
                    alert('order was sent');
                },
                error: function (xhr, resp, text) {
                    $('#error_personEmail').empty();
                    console.log(xhr);
                    var fieldErrorDTOs = xhr.responseJSON.fieldErrorDTOs;
                    $.each(fieldErrorDTOs, function(i, object){
                        var error = document.getElementById("error_" + object.field);
                        error.appendChild(document.createTextNode(object.message));
                        error.appendChild(document.createElement('br'));
                    })
                }
            });
            //sendEntity(getBucketUrl, bucket, function(){
            //    alert('order was sent')
            //})
        }
    });
    if (localStorage.getItem){
        console.log("localStorage Log");

        if (localStorage.getItem(bucketName) == null){
            console.log("localStorage bucket name is null");

            getEntity(getBucketUrl.concat("/new"), setBucketToLocalStorage);
        }
        console.log("localStorage bucket name not null");
        console.log(getBucket());


    }else{
        console.log("cookieLog");

        if(getCookie(bucketName) == null){
            getEntity(getBucketUrl.concat("/new"), setBucketToCookie);
        }
    }


});

function setBucketToLocalStorage(data){
    console.log(data);
    console.log("LocalStorage base setup");
    var dataToSave = JSON.stringify(data);
    localStorage.userBucket = dataToSave;

    console.log(getBucket());

}

function setBucketToCookie(data){
    console.log(data);

    setCookie(bucketName, data, 10)
}

function setCookie(c_name, data, exdays){
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var c_value = JSON.stringify(data) + ((exdays == null) ? "" : "; expires=" + exdate.toUTCString());
    document.cookie = c_name + "=" + c_value;
}

function getCookie(c_name){
    var nameEQ = c_name + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++){
        var c = ca[i];
        while (c.charAt(0) == ' '){
            c = c.substring(1);
        }
        if (c.indexOf(nameEQ) == 0){
            return JSON.parse(c.substring(nameEQ.length, c.length));
        }
    }
    return null;
}

function getBucket(){
    if (localStorage.getItem) {
        return JSON.parse(localStorage.getItem(bucketName));
    }
    else{
        return getCookie(bucketName);
    }
}

function saveBucket(data){
    console.log('saveBucket');
    if (localStorage.getItem){
        console.log('saveBucket localStorage');

        setBucketToLocalStorage(data);
    }else{
        console.log('saveBucket cookie');

        setBucketToCookie(data);
    }
}
function addProductToCard(productId){
    var bucket = getBucket();

    var product = productList.filter(function(obj){
        return obj.productId == productId;
    });

    bucket.orderedProducts.push(product[0]);
    bucket.size++;
    bucket.totalPrice += product[0].price;

    saveBucket(bucket);
    console.log(bucket)
}

function removeFromCard(product) {
    var bucket = getBucket();
    bucket.orderedProducts.splice(productList.indexOf(product), 1);
    bucket.size--;
    bucket.totalPrice -= product.price;
    console.log(bucket);
    saveBucket(bucket);
    createProductListForBucket();
}
function createProductListForBucket() {
    var $productList = $('.container');

    productList = getBucket().orderedProducts;

    console.log(productList);


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

        var removeFromCardButton = document.createElement("button");
        removeFromCardButton.onclick = function () {
            removeFromCard(product);
        };
        removeFromCardButton.appendChild(document.createTextNode("remove to Bucket"));

        col.appendChild(removeFromCardButton);

        row.appendChild(col);
        $productList.append(row);
    });
    var rowSize = document.createElement('div');
    rowSize.className = 'row';
    var size = document.createElement('h3');
    size.appendChild(document.createTextNode("size " + getBucket().size));
    rowSize.appendChild(size);

    var priceRow = document.createElement('div');
    priceRow.className = 'row';
    var price = document.createElement('h3');
    price.appendChild(document.createTextNode("total Price " + getBucket().totalPrice));
    priceRow.appendChild(price);

    $productList.append(priceRow);
    $productList.append(rowSize);
}