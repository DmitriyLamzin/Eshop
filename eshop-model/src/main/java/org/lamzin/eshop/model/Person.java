package org.lamzin.eshop.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Dmitriy on 13.06.2016.
 */
@Entity
public class Person {
    @Id
    private String email;

    private String firstName;

    private String lastName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}