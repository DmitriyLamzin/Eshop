package org.lamzin.eshop.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.lamzin.eshop.entites.OrderCard;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitriy on 14.06.2016.
 */

public class OrderCardExtendedDto extends ResourceSupport {

    private long orderCardId;
    private List<ProductBasicDto> orderedProducts = new ArrayList<ProductBasicDto>();
    private int size;
    private double totalPrice = 0;
    @Email @NotEmpty
    private String personEmail = "";


    public OrderCardExtendedDto() {
    }

    public OrderCardExtendedDto(OrderCard orderCard, List<ProductBasicDto> productBasicDtoList){

        this.orderCardId = orderCard.getId();
        this.orderedProducts.addAll(productBasicDtoList);
        this.size = orderedProducts.size();
        this.totalPrice = orderCard.getTotalPrice();
        if (orderCard.getPerson() != null)
            this.personEmail = orderCard.getPersonId();
        else this.personEmail = "unknown";

    }


    public long getOrderCardId() {
        return orderCardId;
    }

    public List<ProductBasicDto> getOrderedProducts() {
        return orderedProducts;
    }

    public int getSize() {
        return size;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getPersonEmail() {
        return personEmail;
    }
}
