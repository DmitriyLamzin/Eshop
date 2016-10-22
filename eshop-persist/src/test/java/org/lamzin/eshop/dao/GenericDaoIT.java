package org.lamzin.eshop.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.lamzin.eshop.dao.interfaces.GenericDao;
import org.lamzin.eshop.model.catalog.SubCategory;
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

@ContextConfiguration(locations = "classpath:applicationContext-persist-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DatabaseSetup("/subcategoryDataset.xml")
@Component
public class GenericDaoIT {

    private static final String ID_ENTITY_TO_SAVE = "id_entityToSave";
    private static final String NAME_ENTITY_TO_SAVE = "name_entityToSave";
    private static final String ID_SUB_CATEGORY_FOR_DAO_TEST = "id_subCategoryForDaoTest";
    private static final String NAME_SUB_CATEGORY_FOR_DAO_TEST = "name_subCategoryForDaoTest";
    private static final String ID_ENTITY_DOES_NOT_EXIST = "id_EntityDoesNotExist";
    private static final String NAME_NEW_NAME = "name_newName";

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
        subCategory.setSubCategoryId(ID_ENTITY_TO_SAVE);
        subCategory.setSubCategoryName(NAME_ENTITY_TO_SAVE);

        genericDao.save(subCategory);

        entityManager.flush();
        entityManager.detach(subCategory);

        SubCategory savedEntity = entityManager.find(SubCategory.class, subCategory.getSubCategoryId());
        Assert.assertNotNull(savedEntity);

        Assert.assertEquals(NAME_ENTITY_TO_SAVE, savedEntity.getSubCategoryName());
    }



    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    @Rollback(true)
    public void testSaveThrowsException() {

        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("The entity with this data is already exists");
        thrown.expectMessage("may not be empty");

        SubCategory sameIdSubCategory = new SubCategory();
        sameIdSubCategory.setSubCategoryId(ID_SUB_CATEGORY_FOR_DAO_TEST);

        genericDao.save(sameIdSubCategory);
        entityManager.flush();

    }

    @Test
    @Transactional
    @Rollback(true)
    public void testRetrieve(){

        Assert.assertEquals(NAME_SUB_CATEGORY_FOR_DAO_TEST,
                genericDao.findById(ID_SUB_CATEGORY_FOR_DAO_TEST).getSubCategoryName());

    }

    @Test(expected = NoResultException.class)
    @Transactional
    @Rollback(true)
    public void testRetrieveThrowsException(){
        genericDao.findById(ID_ENTITY_DOES_NOT_EXIST);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdate(){


        SubCategory updatingSubCategory  = new SubCategory();
        updatingSubCategory.setSubCategoryId(ID_SUB_CATEGORY_FOR_DAO_TEST);
        updatingSubCategory.setSubCategoryName(NAME_NEW_NAME);



        genericDao.update(updatingSubCategory);
        Assert.assertEquals(NAME_NEW_NAME,
                entityManager.find(SubCategory.class, ID_SUB_CATEGORY_FOR_DAO_TEST).getSubCategoryName());
        Assert.assertNotEquals(ID_SUB_CATEGORY_FOR_DAO_TEST,
                entityManager.find(SubCategory.class, ID_SUB_CATEGORY_FOR_DAO_TEST).getSubCategoryName());
    }


    @Test
    @Transactional
    @Rollback(true)
    public void testRemove() {
        genericDao.delete(ID_SUB_CATEGORY_FOR_DAO_TEST);

        Assert.assertNull(entityManager.find(SubCategory.class, ID_SUB_CATEGORY_FOR_DAO_TEST));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCount(){
        Assert.assertEquals(1, genericDao.count().longValue());

        entityManager.remove(entityManager.find(SubCategory.class, ID_SUB_CATEGORY_FOR_DAO_TEST));

        Assert.assertEquals(0, genericDao.count().longValue());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testExists(){
        Assert.assertTrue(genericDao.exists(ID_SUB_CATEGORY_FOR_DAO_TEST));
        Assert.assertFalse(genericDao.exists(ID_ENTITY_DOES_NOT_EXIST));
    }

}
