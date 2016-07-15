package org.lamzin.eshop.service;

import org.lamzin.eshop.dao.interfaces.ProductDao;

import org.lamzin.eshop.model.catalog.Product;
import org.lamzin.eshop.service.interfaces.ProductService;
import org.lamzin.eshop.service.interfaces.SubcategoryService;
import org.lamzin.eshop.validation.ExistenceCheckable;

import java.util.List;
@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService, ExistenceCheckable<Long> {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private SubcategoryService subcategoryService;

    public List<Product> getMultipleProducts(String subCategoryId, String categoryId,
                                             Double minPrice,
                                             Double maxPrice,
                                             List<String> producer,
                                             String orderBy,
                                             int page,
                                             int pageSize) {

        return productDao.getMultipleProducts(subCategoryId, minPrice, maxPrice, producer, orderBy, page, pageSize);
    }



    public Product addProduct(Product product, String subCategoryId, String categoryId) {
        product.setSubCategory(subcategoryService.getSubcategory(subCategoryId, categoryId));

        return productDao.save(product);
    }

    public Product updateProduct(Product product, String subCategoryId, String categoryId){
        productDao.findById(categoryId, subCategoryId, product.getId());
        product.setSubCategory(subcategoryService.getSubcategory(subCategoryId,categoryId));
        return productDao.update(product);
    }

    public Product getProductById(long productId, String subCategoryId, String categoryId) {

        return productDao.findById(categoryId, subCategoryId, productId);
    }

    public Product getProductById(long id) {
        return productDao.findById(id);
    }

    public void deleteProduct(long id) {
        productDao.delete(id);
    }

    public void deleteProduct(long productId, String subCategoryId, String categoryId) {
            productDao.delete(categoryId, subCategoryId, productId);

    }

    public Long getSize(String subCategoryId, String categoryId, Double minPrice, Double maxPrice, List<String> producer) {
        return productDao.count(subCategoryId, minPrice, maxPrice, producer);
    }

    public boolean exists(Long id) {
        return productDao.exists(id);
    }
}