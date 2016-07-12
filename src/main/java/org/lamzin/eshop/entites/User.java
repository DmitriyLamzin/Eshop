package org.lamzin.eshop.entites;

import java.util.Date;

/**
 * Created by Dmitriy on 04/11/2015.
 */


public class User {
    long id;
    String firstName, secondName;
//    Role role;
    String password;
    OrderCard orderCard;
    Date registrationDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OrderCard getOrderCard() {
        return orderCard;
    }

    public void setOrderCard(OrderCard orderCard) {
        this.orderCard = orderCard;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
