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
@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DatabaseSetup("/productDataset.xml")
@Component
public class ITProductDaoTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductDao productDao;


    @Test
    @Transactional
    @Rollback(true)
    public void testFindById(){
        Product product = productDao.findById("id_categorySaved", "id_subCategoryForDaoTest", 1);

        Assert.assertEquals("name_ProductSavedAtDatabase", product.getName());

    }

    @Test(expected = NoResultException.class)
    @Transactional
    @Rollback(true)
    public void testFindByIdThrowsException(){
         productDao.findById("id_categorySaved", "id_subCategoryForDaoTest", 10);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDelete(){

    Assert.assertNotNull(entityManager.find(Product.class, 1L));

    productDao.delete("id_categorySaved", "id_subCategoryForDaoTest", 1);

    Assert.assertNull(entityManager.find(Product.class, 1L));


    }

    @Test(expected = NoResultException.class)
    @Transactional
    @Rollback(true)
    public void testDeleteThrowsException(){
        Assert.assertNull(entityManager.find(Product.class, 10L));

        productDao.delete("id_categorySaved", "id_subCategoryForDaoTest", 10);
    }


    @Test
    @Transactional
    @Rollback(true)
    public void testCount(){
        Assert.assertEquals(1, productDao.count("id_subCategoryForDaoTest", 0.0, 5000.0, new ArrayList<String>()).longValue());
        Assert.assertEquals(0, productDao.count("id_subCategoryForDaoTest", 0.0, 0.0, new ArrayList<String>()).longValue());

    }
}
