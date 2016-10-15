package org.lamzin.eshop.dto;

import java.util.ArrayList;
import java.util.List;

public class MultipleProductsRequestObject {
    private double minPrice = 0.0;
    private double maxPrice = 100000;
    private List<String> producer = new ArrayList<String>();
    private String orderBy = "price";
    private int page = 1;
    private int pageSize = 20;

    public MultipleProductsRequestObject() {
    }

    public MultipleProductsRequestObject(MultipleProductsRequestObject multipleProductsRequestObject) {
        this.minPrice = multipleProductsRequestObject.getMinPrice();
        this.maxPrice = multipleProductsRequestObject.getMaxPrice();
        this.producer = multipleProductsRequestObject.getProducer();
        this.orderBy = multipleProductsRequestObject.getOrderBy();
        this.page = multipleProductsRequestObject.getPage();
        this.pageSize = multipleProductsRequestObject.getPageSize();
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<String> getProducer() {
        return producer;
    }

    public void setProducer(List<String> producer) {
        this.producer = producer;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
