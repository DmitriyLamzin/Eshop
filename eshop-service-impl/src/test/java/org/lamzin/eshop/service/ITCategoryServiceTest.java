package org.lamzin.eshop.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.lamzin.eshop.model.catalog.Category;
import org.lamzin.eshop.model.catalog.SubCategory;
import org.lamzin.eshop.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

@ContextConfiguration(locations = "classpath:applicationContext-service-impl-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DatabaseSetup("/productDataset.xml")
@Transactional
@Rollback(true)
public class ITCategoryServiceTest {

    private static final String ID_ENTITY_TO_SAVE = "id_entityToSave";
    private static final String NAME_ENTITY_TO_SAVE = "name_entityToSave";
    private static final String ID_CATEGORY_SAVED = "id_categorySaved";



    @Autowired
    @Qualifier("categoryService")
    private CategoryService categoryService;

    @PersistenceContext
    private EntityManager entityManager;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testSave(){
        Category category = new Category();
        category.setSubCategories(new ArrayList<SubCategory>());
        category.setCategoryId(ID_ENTITY_TO_SAVE);
        category.setName(NAME_ENTITY_TO_SAVE);

        categoryService.addCategory(category);

        entityManager.flush();
        entityManager.detach(category);

        Category savedEntity = entityManager.find(Category.class, category.getCategoryId());
        Assert.assertNotNull(savedEntity);

        Assert.assertEquals(NAME_ENTITY_TO_SAVE, savedEntity.getName());
    }

    @Test
    public void testDelete(){

        categoryService.deleteCategory(ID_CATEGORY_SAVED);

        entityManager.flush();

        Assert.assertNull(entityManager.find(Category.class, ID_CATEGORY_SAVED));
    }


}
