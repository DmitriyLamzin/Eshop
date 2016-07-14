package org.lamzin.eshop.entites.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.lamzin.eshop.entites.OrderCard;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by Dmitriy on 14.06.2016.
 */
public class OrderCardBasicDto extends ResourceSupport {
    private long orderId;
    private int size;
    private double totalPrice;

    @Email @NotEmpty
    private String personEmail;

    public OrderCardBasicDto(OrderCard orderCard) {
        this.orderId = orderCard.getId();
        this.size = orderCard.getOrderSize();
        this.totalPrice = orderCard.getTotalPrice();
        if (orderCard.getPerson() != null)
            this.personEmail = orderCard.getPersonId();
        else this.personEmail = "unknown";
    }

    public long getOrderId() {
        return orderId;
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
