package org.lamzin.eshop.dao.test.integration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.lamzin.eshop.dao.interfaces.GenericDao;
import org.lamzin.eshop.model.catalog.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Dmitriy on 15.06.2016.
 */

@ContextConfiguration(locations = "classpath:applicationContext-persist-test.xml")
@RunWith(Parameterized.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DatabaseSetup("/subcategoryDataset.xml")
public class GenericDaoIT {

    private static final String ID_ENTITY_TO_SAVE = "id_entityToSave";
    private static final String NAME_ENTITY_TO_SAVE = "name_entityToSave";
    private static final String ID_SUB_CATEGORY_FOR_DAO_TEST = "id_subCategoryForDaoTest";
    private static final String NAME_SUB_CATEGORY_FOR_DAO_TEST = "name_subCategoryForDaoTest";
    private static final String ID_ENTITY_DOES_NOT_EXIST = "id_EntityDoesNotExist";
    private static final String NAME_NEW_NAME = "name_newName";
    private static final int ARRAY_SIZE = 4;

    private TestContextManager testContextManager;

    @PersistenceContext
    private EntityManager entityManager;

    @ClassRule
    public static final SpringClassRule SCR = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    private GenericDao<SubCategory, String> genericDao;

    private final Logger log = Logger.getLogger(this.getClass());

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private int pageSize;
    private int pageNumber;
    private int expectedArraySize;

    public GenericDaoIT(int pageNumber, int pageSize, int expectedArraySize) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.expectedArraySize = expectedArraySize;
    }

    @Parameterized.Parameters
    public static Collection<Object []> data(){
        return Arrays.asList(new Object[][]{
                {1, 3, 3},
                {2, 3, 1},
                {1, 4, 4},
                {2, 1, 1},
                {4, 1, 1},
                {2, 2, 2}
        });
    }

    @Before
    public void setUp() throws Exception {
        this.testContextManager = new TestContextManager(getClass());
        this.testContextManager.prepareTestInstance(this);
    }

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
    public void testFindAll(){
        List<SubCategory> subCategories = genericDao.findAll();

        Assert.assertEquals(ARRAY_SIZE, subCategories.size());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testFindAllWithPagination(){
        List<SubCategory> subCategories = genericDao.findAll(pageNumber, pageSize);

        Assert.assertEquals(expectedArraySize, subCategories.size());
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
        Assert.assertEquals(ARRAY_SIZE, genericDao.count().longValue());

        entityManager.remove(entityManager.find(SubCategory.class, ID_SUB_CATEGORY_FOR_DAO_TEST));

        Assert.assertEquals(ARRAY_SIZE-1, genericDao.count().longValue());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testExists(){
        Assert.assertTrue(genericDao.exists(ID_SUB_CATEGORY_FOR_DAO_TEST));
        Assert.assertFalse(genericDao.exists(ID_ENTITY_DOES_NOT_EXIST));
    }

}
