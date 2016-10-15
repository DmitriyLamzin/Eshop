var orderlList;
var orderListRequestUri = '/rest/card';

function createOrderList(data){
    console.log(data);
    var $orderList = $('.container');
    $orderList.empty();

    orderlList = data._embedded.orderCardExtendedDtoList;
    $.each(orderlList, function(i, order) {
        var row = document.createElement('div');
        row.className = 'row';
        var col = document.createElement('div');
        col.className = 'col-lg-7';
        var header = document.createElement('h3');
        header.appendChild(document.createTextNode(order.orderCardId));

        var orderItems = document.createElement('ul');
        $.each(order.orderItems, function(i, orderItem) {
            var li = document.createElement('li');
            var product = document.createTextNode(orderItem.product.name + " ");
            var productId = document.createTextNode(orderItem.product.productId + " ");
            var productPrice = document.createTextNode(orderItem.product.price + " x ");
            var size = document.createTextNode(orderItem.size + " = ");
            var totalPrice = document.createTextNode(orderItem.price);
            li.appendChild(productId);
            li.appendChild(product);
            li.appendChild(productPrice);
            li.appendChild(size);
            li.appendChild(totalPrice);
            orderItems.appendChild(li);
        });

        var price = document.createElement('p');
        price.appendChild(document.createTextNode(localizedMessages['lbl.total.price'] + " " + order.price));

        col.appendChild(header);
        col.appendChild(orderItems);
        col.appendChild(price);

        var deleteButton = document.createElement('button');
        deleteButton.onclick = function () {
            if (confirm(localizedMessages['lbl.removing.acceptance']) == true) {
                deleteCategory(data._links.self, createOrderList)
            }
        };
        deleteButton.appendChild(document.createTextNode(localizedMessages['lbl.delete']));


        col.appendChild(deleteButton);

        row.appendChild(col);
        $orderList.append(row);


        $orderList.append(document.createElement('br'));
    });
}