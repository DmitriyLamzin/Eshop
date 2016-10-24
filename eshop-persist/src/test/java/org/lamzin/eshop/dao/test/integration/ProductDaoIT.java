package org.lamzin.eshop.dao.test.integration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.lamzin.eshop.dao.interfaces.ProductDao;
import org.lamzin.eshop.model.catalog.Product;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Dmitriy on 16.06.2016.
 */
@ContextConfiguration(locations = "classpath:applicationContext-persist-test.xml")
@RunWith(Parameterized.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DatabaseSetup("/productDataset.xml")
public class ProductDaoIT {

    private static final String HP = "HP";
    private static final String ASUS = "ASUS";
    private static final String LENOVO = "Lenovo";
    private TestContextManager testContextManager;

    private static final String ID_CATEGORY_SAVED = "id_categorySaved";
    private static final String ID_SUB_CATEGORY_FOR_DAO_TEST = "id_subCategoryForDaoTest";
    private static final String NAME_PRODUCT_SAVED_AT_DATABASE = "name_ProductSavedAtDatabase";
    private static final long PRODUCT_ID_IS_NOT_PERSISTED = 10;
    private static final long PRODUCT_ID_IS_PERSISTED = 1L;
    private static final int EXPECTED_COUNT_OF_ENTITIES = 1;
    private static final double MIN_PRICE = 0.0;
    private static final double MAX_PRICE = 5000.0;

//    @Parameterized.Parameter(value = 0)
    private String orderBy;
//    @Parameterized.Parameter(value = 1)
    private int page;
//    @Parameterized.Parameter(value = 2)
    private int pageSize;
//    @Parameterized.Parameter(value = 3)
    private double minPrice;
//    @Parameterized.Parameter(value = 4)
    private double maxPrice;
//    @Parameterized.Parameter(value = 5)
    private ArrayList<String> producers;
//    @Parameterized.Parameter(value = 6)
    private int expectedCount;
//    @Parameterized.Parameter(value = 7)
    private int expectedArraySize;


    @ClassRule
    public static final SpringClassRule SCR = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    public ProductDaoIT(String orderBy,
                        int page,
                        int pageSize,
                        double minPrice,
                        double maxPrice,
                        ArrayList<String> producers,
                        int expectedCount, int expectedArraySize) {
        this.orderBy = orderBy;
        this.page = page;
        this.pageSize = pageSize;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.producers = producers;
        this.expectedCount = expectedCount;
        this.expectedArraySize = expectedArraySize;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductDao productDao;


    @Before
    public void setUp() throws Exception {
        this.testContextManager = new TestContextManager(getClass());
        this.testContextManager.prepareTestInstance(this);
    }

    @Parameterized.Parameters
    public static Collection<Object []> data(){
        ArrayList<String> allProducers = new ArrayList<String>(Arrays.asList(HP, ASUS, LENOVO));
        ArrayList<String> hpAsusProducers = new ArrayList<String>(Arrays.asList(HP, ASUS));
        ArrayList<String> asusLenovoProducers = new ArrayList<String>(Arrays.asList(ASUS, LENOVO));

        return Arrays.asList(new Object[][]{
                {"price", 1, 3, 0.0, 3.0, allProducers, 3, 3},
                {"price", 1, 1, 0.0, 3.0, allProducers, 3, 1},
                {"price", 1, 1, 0.0, 2.0, allProducers, 2, 1},
                {"price", 1, 1, 0.0, 1.0, allProducers, 1, 1},
                {"price", 1, 1, 0.0, 0.0, allProducers, 0, 0},
                {"price", 1, 3, 0.0, 3.0, asusLenovoProducers, 2, 2},

        });
    }



    @Test
    @Transactional
    @Rollback(true)
    public void testFindById(){
        Product product = productDao.findById(ID_CATEGORY_SAVED, ID_SUB_CATEGORY_FOR_DAO_TEST,
                PRODUCT_ID_IS_PERSISTED);

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

    productDao.delete(ID_CATEGORY_SAVED, ID_SUB_CATEGORY_FOR_DAO_TEST, PRODUCT_ID_IS_PERSISTED);

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
        Assert.assertEquals(expectedCount,
                productDao.count(ID_SUB_CATEGORY_FOR_DAO_TEST, minPrice, maxPrice, producers).longValue());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testGetMultipleProduct(){
        List<Product> products = productDao.getMultipleProducts(ID_SUB_CATEGORY_FOR_DAO_TEST,
                minPrice,
                maxPrice,
                producers,
                orderBy,
                page,
                pageSize);

        Assert.assertEquals(expectedArraySize, products.size());

    }
}
