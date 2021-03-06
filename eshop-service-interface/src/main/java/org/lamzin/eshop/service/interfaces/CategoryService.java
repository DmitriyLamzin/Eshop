package org.lamzin.eshop.service.interfaces;


import org.lamzin.eshop.model.catalog.Category;

import java.util.List;

/**
 * Created by Dmitriy on 16/02/2016.
 */
public interface CategoryService {

    Category addCategory(Category category);

    List<Category> getAllCategories();

    Category updateCategory(Category category);

    Category getCategoryByName(String category);

    void deleteCategory(String category);

    boolean exists(String string);
}
