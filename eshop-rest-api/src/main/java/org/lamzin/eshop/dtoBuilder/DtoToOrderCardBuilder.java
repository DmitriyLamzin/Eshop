package org.lamzin.eshop.services.dtoBuilders;

import org.lamzin.eshop.entites.OrderCard;
import org.lamzin.eshop.entites.Person;
import org.lamzin.eshop.entites.dto.OrderCardExtendedDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Dmitriy on 28.06.2016.
 */
@Service
public class DtoToOrderCardBuilder {


    @Autowired
    DtoToProductBuilder dtoToProductBuilder;

    public OrderCard buildOrderCard(OrderCardExtendedDto orderCardExtendedDto){
        OrderCard orderCard = new OrderCard();
        Person person = new Person();
        person.setEmail(orderCardExtendedDto.getPersonEmail());

        orderCard.setPerson(person);
        orderCard.setProducts(dtoToProductBuilder.buildProductList(orderCardExtendedDto.getOrderedProducts()));
        return orderCard;
    }
}
