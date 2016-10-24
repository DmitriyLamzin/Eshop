package org.lamzin.eshop.dao.test.integration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lamzin.eshop.dao.interfaces.SubCategoryDao;
import org.lamzin.eshop.model.catalog.Product;
import org.lamzin.eshop.model.catalog.SubCategory;
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

@ContextConfiguration(locations = "classpath:applicationContext-persist-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DatabaseSetup("/productDataset.xml")
@Component
public class SubCategoryDaoIT {
    private static final String ID_CATEGORY_SAVED = "id_categorySaved";
    private static final String ID_SUB_CATEGORY_FOR_DAO_TEST = "id_subCategoryForDaoTest";
    private static final String NAME_SUB_CATEGORY_FOR_DAO_TEST = "name_subCategoryForDaoTest";
    private static final String ID_NOT_SAVED_SUB_CATEGORY = "id_notSavedSubCategory";
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SubCategoryDao subCategoryDao;

    @Test
    @Transactional
    @Rollback(true)
    public void testFindById(){
        SubCategory subCategory = subCategoryDao.findById(ID_CATEGORY_SAVED, ID_SUB_CATEGORY_FOR_DAO_TEST);

        Assert.assertEquals(NAME_SUB_CATEGORY_FOR_DAO_TEST, subCategory.getSubCategoryName());

    }

    @Test(expected = NoResultException.class)
    @Transactional
    @Rollback(true)
    public void testFindByIdThrowsException(){
        subCategoryDao.findById(ID_CATEGORY_SAVED, ID_NOT_SAVED_SUB_CATEGORY);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDelete(){

        Assert.assertNotNull(entityManager.find(SubCategory.class, ID_SUB_CATEGORY_FOR_DAO_TEST));

        subCategoryDao.delete(ID_CATEGORY_SAVED, ID_SUB_CATEGORY_FOR_DAO_TEST);

        Assert.assertNull(entityManager.find(SubCategory.class, ID_SUB_CATEGORY_FOR_DAO_TEST));


    }

    @Test(expected = NoResultException.class)
    @Transactional
    @Rollback(true)
    public void testDeleteThrowsException(){
        Assert.assertNull(entityManager.find(SubCategory.class, ID_NOT_SAVED_SUB_CATEGORY));

        subCategoryDao.delete(ID_CATEGORY_SAVED, ID_SUB_CATEGORY_FOR_DAO_TEST);
    }

}
