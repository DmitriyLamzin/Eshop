package org.lamzin.eshop.dto;

import org.lamzin.eshop.model.catalog.SubCategory;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by Dmitriy on 31.05.2016.
 */
public class SubcategoryBasicDto extends ResourceSupport {

    private String subcategoryId;
    private String subcategoryName;

    public SubcategoryBasicDto(SubCategory subCategory) {
        this.subcategoryId = subCategory.getSubCategoryId();
        this.subcategoryName = subCategory.getSubCategoryName();
    }

    public String getSubcategoryId() {
        return subcategoryId;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }
}
