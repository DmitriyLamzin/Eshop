package org.lamzin.eshop.dto;

import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

/**
 * Created by Dmitriy on 26.09.2016.
 */
public class OrderItemDto extends ResourceSupport {
    @NotNull
    private ProductBasicDto product;
    @NotNull
    private int size;

    private double price;

    public OrderItemDto(ProductBasicDto productBasicDto, int size, double price) {
        this.product = productBasicDto;
        this.size = size;
        this.price = price;
    }

    public OrderItemDto() {
    }

    public ProductBasicDto getProduct() {
        return product;
    }

    public void setProduct(ProductBasicDto product) {
        this.product = product;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
