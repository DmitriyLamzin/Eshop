package org.lamzin.eshop.dao.catalog.interfaces;


import org.lamzin.eshop.entites.catalog.Category;
import org.lamzin.eshop.entites.catalog.Product;
import org.lamzin.eshop.entites.catalog.SubCategory;

import java.util.List;

/**
 * Created by Dmitriy on 04/11/2015.
 */
public interface ProductDao extends GenericDao<Product, Long> {

    Product findById(String categoryId, String subcategoryId, long productId);

    List<Product> getMultipleProducts(String subCategory,
                                      double minPrice,
                                      double maxPrice,
                                      List<String> producer,
                                      String orderBy,
                                      int page,
                                      int amountPerPage);

    void delete(String categoryId, String subcategoryId, long productId);

    Long count(String subCategory,
               double minPrice,
               double maxPrice,
               List<String> producer);
}
