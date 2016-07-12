package org.lamzin.eshop.services;

import org.lamzin.eshop.entites.catalog.SubCategory;
import org.lamzin.eshop.entites.dto.SubcategoryBasicDto;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Dmitriy on 05.05.2016.
 */
public interface SubcategoryService {
    SubCategory save(@Valid SubCategory subCategoryToSave, String categoryId);

    SubCategory update(SubCategory subCategoryToSave, String categoryId);

    List<SubCategory> getSubcategories(String categoryId);

    void deleteSubcategory (String subcategoryId, String categoryId);

    SubCategory getSubcategory(String subCategoryId, String categoryId);
}
