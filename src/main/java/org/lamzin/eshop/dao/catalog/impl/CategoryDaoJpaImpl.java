package org.lamzin.eshop.dao.catalog.impl;

import org.lamzin.eshop.dao.catalog.interfaces.CategoryDao;
import org.lamzin.eshop.entites.catalog.Category;
import org.lamzin.eshop.entites.catalog.SubCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dmitriy on 04/11/2015.
 */
@Repository ("categoryDao")
public class CategoryDaoJpaImpl extends GenericDaoJpaImpl<Category, String> implements CategoryDao {
    public CategoryDaoJpaImpl() {
        super(Category.class);
    }
}
