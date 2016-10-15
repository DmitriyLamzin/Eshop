package org.lamzin.eshop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.lamzin.eshop.model.catalog.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Dmitriy on 04/11/2015.
 */
@Entity
public class OrderCard {

    @Id
    @GeneratedValue
    private long id;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    @ManyToOne(targetEntity = Person.class, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Person person;

    public OrderCard() {
    }

    @JsonProperty
    public int getOrderSize() {
        int size = 0;
        for (OrderItem orderItem : orderItems){
            size += orderItem.getSize();
        }
        return size;
    }

    public double getTotalPrice() {
        double price = 0.0;
        for (OrderItem orderItem : orderItems) {
            price += orderItem.getPrice();
        }
        return price;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void addOrderItem(Product product, int size) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().equals(product)) {
                orderItem.setSize(orderItem.getSize() + size);
                return;
            }
        }
        orderItems.add(new OrderItem(product, size));

    }

    public void removeOrderItem(Product product) {
        Iterator<OrderItem> iterator = orderItems.iterator();
        while (iterator.hasNext()){
            OrderItem orderItem = iterator.next();
            if (orderItem.getProduct().equals(product)) {
                if (orderItem.getSize() > 1){
                    orderItem.remove();
                }else
                    iterator.remove();
                return;
            }
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getPersonId() {
        return this.person.getEmail();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderCard)) return false;

        OrderCard orderCard = (OrderCard) o;

        if (id != orderCard.id) return false;
        if (orderItems != null ? !orderItems.equals(orderCard.orderItems) : orderCard.orderItems != null) return false;
        return !(person != null ? !person.equals(orderCard.person) : orderCard.person != null);

    }
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (orderItems != null ? orderItems.hashCode() : 0);
        result = 31 * result + (person != null ? person.hashCode() : 0);
        return result;
    }
}