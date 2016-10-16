package org.lamzin.eshop.service;

import org.lamzin.eshop.dao.interfaces.CategoryDao;
import org.lamzin.eshop.dao.interfaces.SubCategoryDao;
import org.lamzin.eshop.model.catalog.Category;
import org.lamzin.eshop.model.catalog.SubCategory;
import org.lamzin.eshop.service.interfaces.SubcategoryService;
import org.lamzin.eshop.validation.ExistenceCheckable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Dmitriy on 05.04.2016.
 */
@Service("subcategoryService")
@Transactional
public class SubcategoryServiceImpl implements ExistenceCheckable<String>, SubcategoryService {

    @Autowired
    private SubCategoryDao subCategoryDao;
    @Autowired
    private CategoryDao categoryDao;


    public SubCategory save(SubCategory subCategoryToSave, String categoryId){
        Category category = categoryDao.findById(categoryId);
        SubCategory subCategory = subCategoryDao.save(subCategoryToSave);
        category.getSubCategories().add(subCategory);

        return subCategory;
    }

    public SubCategory update(SubCategory subCategoryToUpdate, String categoryId) {
        subCategoryDao.findById(categoryId, subCategoryToUpdate.getSubCategoryId());

        return subCategoryDao.update(subCategoryToUpdate);
    }

    public List<SubCategory> getSubcategories(String categoryId) {
        return subCategoryDao.findByCategory(categoryId);
    }

    public boolean exists (String id) {
        return subCategoryDao.exists(id);
    }

    public void deleteSubcategory (String subcategoryId, String categoryId){
        Category category = categoryDao.findById(categoryId);
        SubCategory subCategory = subCategoryDao.findById(categoryId, subcategoryId);
        category.deleteSubcategoryFromList(subCategory);
        subCategoryDao.delete(subcategoryId);
    }

    public SubCategory getSubcategory(String subCategoryId, String categoryId) {
        return subCategoryDao.findById(categoryId, subCategoryId);
    }

}
