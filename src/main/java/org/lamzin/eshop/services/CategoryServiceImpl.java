package org.lamzin.eshop.services;

import org.lamzin.eshop.dao.catalog.interfaces.CategoryDao;
import org.lamzin.eshop.entites.catalog.Category;
import org.lamzin.eshop.entites.catalog.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitriy on 16/02/2016.
 */
@Service("categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService, ExistenceCheckable<String> {

    @Autowired
    private CategoryDao categoryDao;

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }


    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public Category addCategory(Category category) {
        return categoryDao.save(category);
    }

    public Category updateCategory(Category category) {
        return categoryDao.update(category);
    }

    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }


    public Category getCategoryByName(String category) {
        return categoryDao.findById(category);
    }

    public void deleteCategory(String category) {
        categoryDao.findById(category).setSubCategories(new ArrayList<SubCategory>());

        categoryDao.delete(category);
    }

    public boolean exists(String string) {
        return categoryDao.exists(string);
    }


}
