package org.lamzin.eshop.dtoBuilder;

import org.lamzin.eshop.controller.rest.CardController;

import org.lamzin.eshop.dto.OrderCardBasicDto;
import org.lamzin.eshop.dto.OrderCardExtendedDto;
import org.lamzin.eshop.dto.OrderItemDto;
import org.lamzin.eshop.dto.ProductBasicDto;
import org.lamzin.eshop.model.OrderCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitriy on 14.06.2016.
 */

@Service
public class OrderCardDtoBuilder {
    @Autowired
    OrderItemDtoBuilter orderItemDtoBuilter;

    public OrderCardBasicDto buildBasic(OrderCard orderCard){

        OrderCardBasicDto orderCardBasicDto = new OrderCardBasicDto(orderCard);
        orderCardBasicDto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CardController.class).
                getOrderCard(orderCard.getId())).
                withSelfRel());
        return orderCardBasicDto;
    }

    public List<OrderCardBasicDto> buildListBasic(List<OrderCard> orderCards){
        List<OrderCardBasicDto> orderCardBasicDtos = new ArrayList<OrderCardBasicDto>();
        for (OrderCard orderCard : orderCards){
            OrderCardBasicDto orderCardBasicDto = new OrderCardBasicDto(orderCard);
            orderCardBasicDto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CardController.class).
                    getOrderCard(orderCard.getId())).
                    withSelfRel());
            orderCardBasicDtos.add(orderCardBasicDto);
        }
        return orderCardBasicDtos;
    }

    public List<OrderCardExtendedDto> buildListExtended(List<OrderCard> orderCards){
        List<OrderCardExtendedDto> orderCardExtendedDtos = new ArrayList<OrderCardExtendedDto>();
        for (OrderCard orderCard : orderCards){
            OrderCardExtendedDto orderCardExtendedDto = buildExtended(orderCard);
            orderCardExtendedDto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CardController.class).
                    getOrderCard(orderCard.getId())).
                    withSelfRel());
            orderCardExtendedDtos.add(orderCardExtendedDto);
        }
        return orderCardExtendedDtos;
    }

    public OrderCardExtendedDto buildExtended(OrderCard orderCard){
        OrderCardExtendedDto orderCardExtendedDto = new OrderCardExtendedDto(orderCard);

        List<OrderItemDto> orderItemDtos = orderItemDtoBuilter.createListBasicDto(orderCard.getOrderItems(), orderCardExtendedDto);
        orderCardExtendedDto.setOrderItems(orderItemDtos);
        orderCardExtendedDto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CardController.class).
                getOrderCard(orderCard.getId())).
                withSelfRel());
        return orderCardExtendedDto;
    }
}