package org.lamzin.eshop.services.dtoBuilders;

import org.lamzin.eshop.controller.rest.ProductController;
import org.lamzin.eshop.controller.rest.SubCategoryController;
import org.lamzin.eshop.model.catalog.SubCategory;
import org.lamzin.eshop.entites.dto.SubcategoryBasicDto;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubCategoryDtoBuilder {
    public SubCategoryDtoBuilder() {
    }

    public SubcategoryBasicDto createBasicDto(String categoryId, SubCategory subCategory) {
        SubcategoryBasicDto basicDto = new SubcategoryBasicDto(subCategory);
        basicDto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SubCategoryController.class).
                getAllSubCategories(categoryId)).
                slash(subCategory.getSubCategoryId()).
                withSelfRel());

        List<String> producers = new ArrayList<String>();
        producers.add("all");

        basicDto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ProductController.class).getMultipleProducts(subCategory.getSubCategoryId(),
                categoryId, 0.0, 650000.0, producers, "price", 1, 25)).withRel("products"));

        return basicDto;
    }

    public List<SubcategoryBasicDto> createListBasicDto(String categoryId, List<SubCategory> subCategories) {
        List<SubcategoryBasicDto> productBasicDtos = new ArrayList<SubcategoryBasicDto>();
        for (SubCategory subCategory : subCategories) {
            productBasicDtos.add(createBasicDto(categoryId, subCategory));
        }
        return productBasicDtos;
    }
}