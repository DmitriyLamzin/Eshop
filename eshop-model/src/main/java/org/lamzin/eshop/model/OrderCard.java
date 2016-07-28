package org.lamzin.eshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.lamzin.eshop.model.catalog.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitriy on 04/11/2015.
 */
@Entity
public class OrderCard {

    @Id @GeneratedValue
    private long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Product> products = new ArrayList<Product>();

    @Transient
    private double totalPrice = 0;

    @ManyToOne(targetEntity = Person.class, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Person person;

    public OrderCard() {
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        calculatePrice();
    }

    @JsonProperty
    public int getOrderSize(){
        return products.size();
    }

    public double getTotalPrice() {
        calculatePrice();
        return totalPrice;
    }

    private void calculatePrice(){
        this.totalPrice = 0;
        if (products.size() > 0) {
            for (Product p : products) {
                this.totalPrice += p.getPrice();
            }
        }
    }

    public void setTotalPrice(double price) {
        this.totalPrice = price;
    }

    public void addProduct(Product product){
        products.add(product);
        calculatePrice();
    }

    public void removeProduct(Product product){
        products.remove(product);
        calculatePrice();
        totalPrice -= product.getPrice();

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
        if (Double.compare(orderCard.totalPrice, totalPrice) != 0) return false;
        if (products != null ? !products.equals(orderCard.products) : orderCard.products != null) return false;
        return !(person != null ? !person.equals(orderCard.person) : orderCard.person != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (products != null ? products.hashCode() : 0);
        temp = Double.doubleToLongBits(totalPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (person != null ? person.hashCode() : 0);
        return result;
    }
}
