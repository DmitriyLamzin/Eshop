package org.lamzin.eshop.services.dtoWrapers;

import org.lamzin.eshop.controllers.rest.ProductController;
import org.lamzin.eshop.entites.catalog.Product;
import org.lamzin.eshop.entites.dto.ProductBasicDto;
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