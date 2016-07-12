package org.lamzin.eshop.services;

import org.lamzin.eshop.dao.catalog.interfaces.CategoryDao;
import org.lamzin.eshop.entites.catalog.Category;
import org.lamzin.eshop.entites.dto.CategoryBasicDto;

import java.util.List;

/**
 * Created by Dmitriy on 16/02/2016.
 */
public interface CategoryService {
    CategoryDao getCategoryDao();

    void setCategoryDao(CategoryDao categoryDao);

    Category addCategory(Category category);

    List<Category> getAllCategories();

    Category updateCategory(Category category);

    Category getCategoryByName(String category);

    void deleteCategory(String category);

    boolean exists(String string);
}
