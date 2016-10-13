package org.lamzin.eshop.dtoBuilder;

import org.lamzin.eshop.controller.rest.ProductController;
import org.lamzin.eshop.dto.ProductBasicDto;
import org.lamzin.eshop.model.catalog.Product;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDtoBuilder {

    public ProductDtoBuilder() {
    }

    public ProductBasicDto createBasicDto(Product product, String subCategoryId, String categoryId) {
        ProductBasicDto p = new ProductBasicDto(product);
        p.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ProductController.class).
                getProductById(product.getId(), subCategoryId, categoryId)).
                withSelfRel());
        return p;
    }

    public ProductBasicDto createBasicDto(Product product) {
        ProductBasicDto p = new ProductBasicDto(product);
        p.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ProductController.class).
                getProductById(product.getId())).
                withSelfRel());
        return p;
    }


    public List<ProductBasicDto> createListBasicDto(List<Product> products) {
        List<ProductBasicDto> productBasicDtos = new ArrayList<ProductBasicDto>();
        for (Product product : products) {
            ProductBasicDto p = new ProductBasicDto(product);
            p.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ProductController.class).
                    getProductById(product.getId())).withSelfRel());
            productBasicDtos.add(p);
        }
        return productBasicDtos;
    }
}