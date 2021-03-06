const getBucketUrl = '/rest/card';
const bucketName = 'userBucket';


$(document).ready(function() {
    var $body = $('body');

    $body.on('click', '#sendOrder', function () {
        var bucket = getBucket();
        bucket.personEmail = $('#userEmail').val();
        if (bucket.orderItems.length < 1){
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
                error: function (xhr) {
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
    var url = getBucketUrl + '/items/' + productId;

    sendEntity(url, bucket, function(data){
        saveBucket(data);
        console.log(data)
    });


}

function removeFromCard(url) {
    var bucket = getBucket();
    $.ajax({
        url: url, // url where to submit the request
        headers: {
            'Authorization':'Basic YWRtaW46cGFzcw==',
            'Content-Type':'application/hal+json'
        },
        type: "DELETE", // type of action POST || GET
        //dataType: 'hal+json', // data type
        data: JSON.stringify(bucket),
        //async: false,
        success: function(data){
            console.log(data);
            saveBucket(data);
            createOrderListForBucket();
        },
        error: function (xhr) {
            console.log(xhr);
        }
    });
}


function createOrderListForBucket() {
    var $productList = $('.container');

    var orderItems = getBucket().orderItems;

    console.log(orderItems);


    $productList.empty();
    $.each(orderItems, function(i, orderItem) {
        var row = document.createElement('div');
        row.className = 'row';
        var col = document.createElement('div');
        col.className = 'col-lg-7';
        var header = document.createElement('h3');
        header.appendChild(document.createTextNode(orderItem.product.productId + " "+ orderItem.product.name));

        var price = document.createElement('p');
        price.appendChild(document.createTextNode(orderItem.product.price + 'X' + orderItem.size));
        var productEntity = document.createElement('p');
        productEntity.appendChild(document.createTextNode(localizedMessages['lbl.producer'] + " " + orderItem.product.producer));
        col.appendChild(header);
        col.appendChild(productEntity);
        col.appendChild(price);

        var orderItemPrice = document.createElement('p');
        orderItemPrice.appendChild(document.createTextNode(localizedMessages['lbl.price'] + " " + orderItem.price));
        col.appendChild(orderItemPrice);


        var removeFromCardButton = document.createElement("button");
        removeFromCardButton.onclick = function () {
            removeFromCard(orderItem._links.self.href);
        };
        removeFromCardButton.appendChild(document.createTextNode(localizedMessages['lbl.from.bucket']));

        col.appendChild(removeFromCardButton);

        row.appendChild(col);
        $productList.append(row);
    });
    var rowSize = document.createElement('div');
    rowSize.className = 'row';
    var size = document.createElement('h3');
    size.appendChild(document.createTextNode(localizedMessages['lbl.size'] + " " + getBucket().size));
    rowSize.appendChild(size);

    var priceRow = document.createElement('div');
    priceRow.className = 'row';
    var price = document.createElement('h3');
    price.appendChild(document.createTextNode(localizedMessages['lbl.total.price'] + " " + getBucket().totalPrice));
    priceRow.appendChild(price);

    $productList.append(priceRow);
    $productList.append(rowSize);
}