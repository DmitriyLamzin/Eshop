package org.lamzin.eshop.dao.impl;

import org.lamzin.eshop.dao.interfaces.CategoryDao;
import org.lamzin.eshop.model.catalog.Category;
import org.springframework.stereotype.Repository;

/**
 * Created by Dmitriy on 04/11/2015.
 */
@Repository ("categoryDao")
public class CategoryDaoJpaImpl extends GenericDaoJpaImpl<Category, String> implements CategoryDao {
    public CategoryDaoJpaImpl() {
        super(Category.class);
    }
}
