package org.lamzin.eshop.entites.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.lamzin.eshop.entites.catalog.Category;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by Dmitriy on 31.05.2016.
 */
public class CategoryBasicDto extends ResourceSupport {
    @JsonProperty
    private String categoryId;
    @JsonProperty
    private String name;

    @JsonCreator
    public CategoryBasicDto(Category category) {
        this.categoryId = category.getCategoryId();
        this.name = category.getName();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }
}
