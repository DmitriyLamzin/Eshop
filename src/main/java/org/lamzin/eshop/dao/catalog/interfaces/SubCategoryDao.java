package org.lamzin.eshop.dao.catalog.interfaces;

import org.lamzin.eshop.entites.catalog.SubCategory;

import java.util.List;

/**
 * Created by Dmitriy on 04/11/2015.
 */
public interface SubCategoryDao extends GenericDao<SubCategory, String>{
    Class<SubCategory> type = SubCategory.class;

    SubCategory findById(String categoryId, String subcategoryId);

    List<SubCategory> findByCategory (String categoryId);

    void delete(String categoryId, String subcategoryId);
}
