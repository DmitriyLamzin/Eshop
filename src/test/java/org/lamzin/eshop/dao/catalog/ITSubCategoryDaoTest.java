package org.lamzin.eshop.dao.catalog;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lamzin.eshop.dao.catalog.interfaces.SubCategoryDao;
import org.lamzin.eshop.entites.catalog.Product;
import org.lamzin.eshop.entites.catalog.SubCategory;
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

/**
 * Created by Dmitriy on 23.06.2016.
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
public class ITSubCategoryDaoTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SubCategoryDao subCategoryDao;

    @Test
    @Transactional
    @Rollback(true)
    public void testFindById(){
        SubCategory subCategory = subCategoryDao.findById("id_categorySaved", "id_subCategoryForDaoTest");

        Assert.assertEquals("name_subCategoryForDaoTest", subCategory.getSubCategoryName());

    }

    @Test(expected = NoResultException.class)
    @Transactional
    @Rollback(true)
    public void testFindByIdThrowsException(){
        subCategoryDao.findById("id_categorySaved", "id_notSavedSubCategory");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDelete(){

        Assert.assertNotNull(entityManager.find(SubCategory.class, "id_subCategoryForDaoTest"));

        subCategoryDao.delete("id_categorySaved", "id_subCategoryForDaoTest");

        Assert.assertNull(entityManager.find(SubCategory.class, "id_subCategoryForDaoTest"));


    }

    @Test(expected = NoResultException.class)
    @Transactional
    @Rollback(true)
    public void testDeleteThrowsException(){
        Assert.assertNull(entityManager.find(SubCategory.class, "id_notSavedSubCategory"));

        subCategoryDao.delete("id_categorySaved", "id_notSavedSubCategory");
    }

}
