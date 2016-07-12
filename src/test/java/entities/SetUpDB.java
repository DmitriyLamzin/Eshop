package entities;

import org.apache.log4j.Logger;
import org.lamzin.eshop.dao.catalog.interfaces.CategoryDao;
import org.lamzin.eshop.dao.catalog.interfaces.ProductDao;
import org.lamzin.eshop.dao.catalog.interfaces.SubCategoryDao;
import org.lamzin.eshop.entites.catalog.Category;
import org.lamzin.eshop.entites.catalog.Product;
import org.lamzin.eshop.entites.catalog.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Dmitriy on 16.06.2016.
 */
@Component
public class SetUpDB {
    private EntityFactory entityFactory;
    private final Logger log;

    private CategoryDao categoryDao;

    private SubCategoryDao subCategoryDao;

    private ProductDao productDao;

    @Autowired
    public SetUpDB(EntityFactory entityFactory, CategoryDao categoryDao, SubCategoryDao subCategoryDao, ProductDao productDao) {
        this.entityFactory = entityFactory;
        this.categoryDao = categoryDao;
        this.subCategoryDao = subCategoryDao;
        this.productDao = productDao;
        this.log = Logger.getLogger(this.getClass());
    }

    @Transactional
    public void setAllUp(){
        List<SubCategory> subCategories = entityFactory.getSubCategories();
        for (SubCategory subCategory : subCategories){
            log.info(subCategory);
            subCategoryDao.save(subCategory);
        }
        List<Category> categories = entityFactory.getCategories();
        for (Category category : categories){
            categoryDao.save(category);
        }
        List<Product> products = entityFactory.getProducts();
        for (Product product : products){
            productDao.save(product);
        }
    }
}
