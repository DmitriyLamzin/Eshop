package org.lamzin.eshop.services.dtoWrapers;

import org.lamzin.eshop.controllers.rest.CategoryController;
import org.lamzin.eshop.controllers.rest.SubCategoryController;
import org.lamzin.eshop.entites.catalog.Category;
import org.lamzin.eshop.entites.dto.CategoryBasicDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Dmitriy on 17.06.2016.
 */
@Service
public class CategoryDtoBuilder {


    public CategoryDtoBuilder() {
    }

    public CategoryBasicDto createBasicDto(Category category){
        CategoryBasicDto categoryBasicDto = new CategoryBasicDto(category);
        categoryBasicDto.add(linkTo(methodOn(CategoryController.class).
                getCategoryById(category.getCategoryId())).
                withSelfRel());

        categoryBasicDto.add(linkTo(methodOn(SubCategoryController.class).
                getAllSubCategories(category.getCategoryId())).
                withRel("subCategories"));

        return categoryBasicDto;
    }

    public List<CategoryBasicDto> createBasicDtoList(List<Category> categoryList) {
        List<CategoryBasicDto> basicDtoList = new ArrayList<CategoryBasicDto>();

        for (Category category : categoryList) {
            basicDtoList.add(createBasicDto(category));
        }
        return basicDtoList;
    }
}
