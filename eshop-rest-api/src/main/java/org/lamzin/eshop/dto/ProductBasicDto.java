package org.lamzin.eshop.dto;

import org.lamzin.eshop.model.catalog.Product;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by Dmitriy on 06.05.2016.
 */
public class ProductBasicDto extends ResourceSupport{

    private long productId;
    private String name;
    private double price;
    private String producer;

    public ProductBasicDto() {
    }

    public ProductBasicDto(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.producer = product.getProducer();
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

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
