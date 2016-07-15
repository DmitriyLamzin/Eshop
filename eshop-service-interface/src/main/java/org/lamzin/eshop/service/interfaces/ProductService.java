package org.lamzin.eshop.service.interfaces;

import org.lamzin.eshop.model.catalog.Product;

import java.util.List;

/**
 * Created by Dmitriy on 03.06.2016.
 */
public interface ProductService {
    List<Product> getMultipleProducts(String subCategoryId, String categoryId,
                                      Double minPrice,
                                      Double maxPrice,
                                      List<String> producer,
                                      String orderBy,
                                      int page,
                                      int pageSize);

    Product addProduct(Product product, String subCategoryId, String categoryId);

    Product updateProduct(Product product, String subCategoryId, String categoryId);

    Product getProductById(long productId, String subCategoryId, String categoryId);

    void deleteProduct(long productId, String subCategoryId, String categoryId);

    Product getProductById(long id);

    void deleteProduct(long id);

    Long getSize(String subCategoryId, String categoryId,
                 Double minPrice,
                 Double maxPrice,
                 List<String> producer);
}
