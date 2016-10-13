package org.lamzin.eshop.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.lamzin.eshop.model.OrderCard;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitriy on 14.06.2016.
 */

public class OrderCardExtendedDto extends ResourceSupport {

    private long orderCardId;
    private List<OrderItemDto> orderItems = new ArrayList<OrderItemDto>();
    private int size;
    private double totalPrice = 0;
    @Email @NotEmpty
    private String personEmail = "";

    public OrderCardExtendedDto() {
    }

    public OrderCardExtendedDto(OrderCard orderCard) {
        this.orderCardId = orderCard.getId();
        this.size = orderCard.getOrderSize();
        this.totalPrice = orderCard.getTotalPrice();
        if (orderCard.getPerson() != null)
            this.personEmail = orderCard.getPersonId();
        else this.personEmail = "unknown";
    }

    public OrderCardExtendedDto(OrderCard orderCard, List<OrderItemDto> orderItemDtos){

        this.orderCardId = orderCard.getId();
        this.orderItems.addAll(orderItemDtos);
        this.size = orderItems.size();
        this.totalPrice = orderCard.getTotalPrice();
        if (orderCard.getPerson() != null)
            this.personEmail = orderCard.getPersonId();
        else this.personEmail = "unknown";

    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    public long getOrderCardId() {
        return orderCardId;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
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
