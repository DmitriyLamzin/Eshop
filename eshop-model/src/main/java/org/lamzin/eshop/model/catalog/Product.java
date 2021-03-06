package org.lamzin.eshop.model.catalog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.lamzin.eshop.model.OrderCard;
import org.lamzin.eshop.model.OrderItem;

import javax.persistence.*;
import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitriy on 04/11/2015.
 */
@Entity
public class Product {
    @Id
    private long id;
    private String name;
    private double price;
    private String producer;

    @ManyToMany
    private List<OrderCard> observerItems = new ArrayList<OrderCard>();

    @ManyToOne(targetEntity = SubCategory.class, cascade = CascadeType.MERGE)
    private SubCategory subCategory;

    @JsonIgnore
    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    @JsonIgnore
    public String getSubCatId(){
        return this.subCategory.getSubCategoryId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (Double.compare(product.price, price) != 0) return false;
        if (!name.equals(product.name)) return false;
        return !(producer != null ? !producer.equals(product.producer) : product.producer != null);

    }

    public void addObserver(OrderCard orderCard){
        if (!observerItems.contains(orderCard)){
            observerItems.add(orderCard);
        }
    }

    public void removeObserver(OrderCard orderCard){
        if (observerItems.contains(orderCard)){
            observerItems.remove(orderCard);
        }
    }

    public List<OrderCard> getObserverItems() {
        return observerItems;
    }

    public void setObserverItems(List<OrderCard> observerItems) {
        this.observerItems = observerItems;
    }

    public boolean hasObservers(){
        return !observerItems.isEmpty();
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (producer != null ? producer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", producer='" + producer + '\'' +
                '}';
    }
}
