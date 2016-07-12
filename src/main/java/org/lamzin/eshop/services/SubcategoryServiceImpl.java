package org.lamzin.eshop.services;

import org.lamzin.eshop.dao.catalog.interfaces.CategoryDao;
import org.lamzin.eshop.dao.catalog.interfaces.SubCategoryDao;
import org.lamzin.eshop.entites.catalog.Category;
import org.lamzin.eshop.entites.catalog.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by Dmitriy on 05.04.2016.
 */
@Service("subcategoryService")
@Transactional
public class SubcategoryServiceImpl implements ExistenceCheckable<String>, SubcategoryService {

    @Autowired
    SubCategoryDao subCategoryDao;
    @Autowired
    CategoryDao categoryDao;


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
//        return true;
    }

    public void deleteSubcategory (String subcategoryId, String categoryId){
            subCategoryDao.delete(categoryId, subcategoryId);
    }

    public SubCategory getSubcategory(String subCategoryId, String categoryId) {
        return subCategoryDao.findById(categoryId, subCategoryId);
    }

}
