package org.lamzin.eshop.dao.test.integration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lamzin.eshop.dao.interfaces.OrderCardDao;
import org.lamzin.eshop.model.OrderCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ContextConfiguration(locations = "classpath:applicationContext-persist-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DatabaseSetup("/orderCardDataset.xml")
public class OrderCardDaoIT {

    private static final String EXISTED_PERSON_EMAIL = "person@email";
    private static final int ARRAY_SIZE = 2;

    @Autowired
    private OrderCardDao orderCardDao;

    @Test
    @Transactional
    @Rollback(true)
    public void testFindAllOrders(){
        List<OrderCard> orderCardList = orderCardDao.findAllOrders(EXISTED_PERSON_EMAIL);

        Assert.assertEquals(ARRAY_SIZE, orderCardList.size());

    }
}
