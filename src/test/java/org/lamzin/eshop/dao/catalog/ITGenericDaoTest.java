package org.lamzin.eshop.dao.catalog;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.lamzin.eshop.dao.catalog.interfaces.GenericDao;
import org.lamzin.eshop.entites.catalog.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

/**
 * Created by Dmitriy on 15.06.2016.
 */

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DatabaseSetup("/subcategoryDataset.xml")
@Component
public class ITGenericDaoTest {

    @PersistenceContext
    private EntityManager entityManager;

    private GenericDao<SubCategory, String> genericDao;

    private final Logger log = Logger.getLogger(this.getClass());

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Autowired
    @Qualifier("GenericDao")
    private void setGenericDao(GenericDao<SubCategory, String> genericDao) {
        this.genericDao = genericDao;
        this.genericDao.setType(SubCategory.class);
    }


    @Test
    @Transactional
    @Rollback(true)
    public void testSave(){
        SubCategory subCategory = new SubCategory();
        subCategory.setSubCategoryId("id_entityToSave");
        subCategory.setSubCategoryName("name_entityToSave");

        genericDao.save(subCategory);

        entityManager.flush();
        entityManager.detach(subCategory);

        SubCategory savedEntity = entityManager.find(SubCategory.class, subCategory.getSubCategoryId());
        Assert.assertNotNull(savedEntity);

        Assert.assertEquals("name_entityToSave", savedEntity.getSubCategoryName());
    }



    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    @Rollback(true)
    public void testSaveThrowsException() {

        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("The entity with this data is already exists");
        thrown.expectMessage("may not be empty");

        SubCategory sameIdSubCategory = new SubCategory();
        sameIdSubCategory.setSubCategoryId("id_subCategoryForDaoTest");

        genericDao.save(sameIdSubCategory);
        entityManager.flush();

    }

    @Test
    @Transactional
    @Rollback(true)
    public void testRetrieve(){

        Assert.assertEquals("name_subCategoryForDaoTest", genericDao.findById("id_subCategoryForDaoTest").getSubCategoryName());

    }

    @Test(expected = NoResultException.class)
    @Transactional
    @Rollback(true)
    public void testRetrieveThrowsException(){
        genericDao.findById("id_EntityDoesNotExist");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdate(){


        SubCategory updatingSubCategory  = new SubCategory();
        updatingSubCategory.setSubCategoryId("id_subCategoryForDaoTest");
        updatingSubCategory.setSubCategoryName("name_newName");



        genericDao.update(updatingSubCategory);
        Assert.assertEquals("name_newName", entityManager.find(SubCategory.class, "id_subCategoryForDaoTest").getSubCategoryName());
        Assert.assertNotEquals("name_subCategoryForDaoTest", entityManager.find(SubCategory.class, "id_subCategoryForDaoTest").getSubCategoryName());
    }


    @Test
    @Transactional
    @Rollback(true)
    public void testRemove() {
        genericDao.delete("id_subCategoryForDaoTest");

        Assert.assertNull(entityManager.find(SubCategory.class, "id_subCategoryForDaoTest"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCount(){
        Assert.assertEquals(1, genericDao.count().longValue());

        entityManager.remove(entityManager.find(SubCategory.class, "id_subCategoryForDaoTest"));

        Assert.assertEquals(0, genericDao.count().longValue());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testExists(){
        Assert.assertTrue(genericDao.exists("id_subCategoryForDaoTest"));
        Assert.assertFalse(genericDao.exists("id_subCategoryDoesNotPersisted"));
    }

}
