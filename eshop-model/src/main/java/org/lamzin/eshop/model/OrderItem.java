package org.lamzin.eshop.model;

import org.lamzin.eshop.model.catalog.Product;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * Created by Dmitriy on 06.09.2016.
 */
@Embeddable
public class OrderItem {
    @ManyToOne
    private Product product;
    private int size;

    public OrderItem(Product product, int size) {
        this.product = product;
        this.size = size;
    }

    public OrderItem() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getPrice(){
        return product.getPrice() * size;
    }

    public void remove() {
        int size = getSize();
        if (size >= 1) {
            setSize(size - 1);
        }
    }
}
