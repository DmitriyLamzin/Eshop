package org.lamzin.eshop.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lamzin.eshop.dao.interfaces.ProductDao;
import org.lamzin.eshop.model.catalog.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

/**
 * Created by Dmitriy on 16.06.2016.
 */
@ContextConfiguration(locations = "classpath:applicationContext-persist-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DatabaseSetup("/productDataset.xml")
@Component
public class ProductDaoIT {

    private static final String ID_CATEGORY_SAVED = "id_categorySaved";
    private static final String ID_SUB_CATEGORY_FOR_DAO_TEST = "id_subCategoryForDaoTest";
    private static final String NAME_PRODUCT_SAVED_AT_DATABASE = "name_ProductSavedAtDatabase";
    private static final long PRODUCT_ID_IS_NOT_PERSISTED = 10;
    private static final long PRODUCT_ID_IS_PERSISTED = 1;
    private static final int EXPECTED_COUNT_OF_ENTITIES = 1;
    private static final double MIN_PRICE = 0.0;
    private static final double MAX_PRICE = 5000.0;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductDao productDao;


    @Test
    @Transactional
    @Rollback(true)
    public void testFindById(){
        Product product = productDao.findById(ID_CATEGORY_SAVED, ID_SUB_CATEGORY_FOR_DAO_TEST, PRODUCT_ID_IS_PERSISTED);

        Assert.assertEquals(NAME_PRODUCT_SAVED_AT_DATABASE, product.getName());

    }

    @Test(expected = NoResultException.class)
    @Transactional
    @Rollback(true)
    public void testFindByIdThrowsException(){
         productDao.findById(ID_CATEGORY_SAVED, ID_SUB_CATEGORY_FOR_DAO_TEST, PRODUCT_ID_IS_NOT_PERSISTED);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDelete(){

    Assert.assertNotNull(entityManager.find(Product.class, PRODUCT_ID_IS_PERSISTED));

    productDao.delete(ID_CATEGORY_SAVED, ID_SUB_CATEGORY_FOR_DAO_TEST, 1);

    Assert.assertNull(entityManager.find(Product.class, PRODUCT_ID_IS_PERSISTED));


    }

    @Test(expected = NoResultException.class)
    @Transactional
    @Rollback(true)
    public void testDeleteThrowsException(){
        Assert.assertNull(entityManager.find(Product.class, PRODUCT_ID_IS_NOT_PERSISTED));

        productDao.delete(ID_CATEGORY_SAVED, ID_SUB_CATEGORY_FOR_DAO_TEST, PRODUCT_ID_IS_NOT_PERSISTED);
    }


    @Test
    @Transactional
    @Rollback(true)
    public void testCount(){
        Assert.assertEquals(EXPECTED_COUNT_OF_ENTITIES, productDao.count(ID_SUB_CATEGORY_FOR_DAO_TEST, MIN_PRICE, MAX_PRICE, new ArrayList<String>()).longValue());
    }
}
