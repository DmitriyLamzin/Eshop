package org.lamzin.eshop.dtoBuilder;

import org.lamzin.eshop.controller.rest.CardController;
import org.lamzin.eshop.dto.OrderCardExtendedDto;
import org.lamzin.eshop.dto.OrderItemDto;
import org.lamzin.eshop.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dmitriy on 26.09.2016.
 */
@Service
public class OrderItemDtoBuilter {

    @Autowired
    private ProductDtoBuilder productDtoBuilder;

    public List<OrderItemDto> createListBasicDto(List<OrderItem> orderItems, OrderCardExtendedDto orderCardExtendedDto) {
        List<OrderItemDto> orderItemDtos = new LinkedList<OrderItemDto>();

        for (OrderItem orderItem : orderItems){
            OrderItemDto orderItemDto = new OrderItemDto(productDtoBuilder.createBasicDto(orderItem.getProduct()),
                    orderItem.getSize(),
                    orderItem.getPrice());
            orderItemDto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CardController.class).
                    deleteOrderItem(orderItem.getProduct().getId(), orderCardExtendedDto)).withSelfRel());
            orderItemDtos.add(orderItemDto);
        }
        return orderItemDtos;
    }
}
