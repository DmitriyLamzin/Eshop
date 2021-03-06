package org.lamzin.eshop.dtoBuilder;

import org.lamzin.eshop.model.OrderCard;
import org.lamzin.eshop.model.Person;
import org.lamzin.eshop.dto.OrderCardExtendedDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Dmitriy on 28.06.2016.
 */
@Service
public class DtoToOrderCardBuilder {


    @Autowired
    private DtoToOrderItem dtoToOrderItem;

    public OrderCard buildOrderCard(OrderCardExtendedDto orderCardExtendedDto){
        OrderCard orderCard = new OrderCard();
        Person person = new Person();
        person.setEmail(orderCardExtendedDto.getPersonEmail());

        orderCard.setPerson(person);
        orderCard.setOrderItems(dtoToOrderItem.buildOrderItems(orderCardExtendedDto.getOrderItems()));
        return orderCard;
    }
}