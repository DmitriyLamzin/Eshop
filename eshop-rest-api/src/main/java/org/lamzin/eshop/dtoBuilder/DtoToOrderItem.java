package org.lamzin.eshop.dtoBuilder;

import org.lamzin.eshop.dto.OrderItemDto;
import org.lamzin.eshop.model.OrderItem;
import org.lamzin.eshop.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitriy on 26.09.2016.
 */
@Service
public class DtoToOrderItem {

    @Autowired
    private ProductService productService;
    public List<OrderItem> buildOrderItems(List<OrderItemDto> orderItems) {
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for (OrderItemDto orderItemDto : orderItems){
            OrderItem orderItem = new OrderItem();
            orderItem.setSize(orderItemDto.getSize());
            orderItem.setProduct(productService.getProductById(orderItemDto.getProduct().getProductId()));
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }
}
