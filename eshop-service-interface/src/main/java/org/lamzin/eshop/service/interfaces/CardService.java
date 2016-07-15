package org.lamzin.eshop.service.interfaces;

import org.lamzin.eshop.dao.interfaces.GenericDao;
import org.lamzin.eshop.model.OrderCard;
import org.lamzin.eshop.model.Person;
import org.lamzin.eshop.model.catalog.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Created by Dmitriy on 15.07.2016.
 */
public interface CardService {
    @Autowired
    @Qualifier("GenericDao")
    void setPersonDao(GenericDao<Person, String> genericDao);

    OrderCard createOrderCard(OrderCard orderCard);

    OrderCard getOrderCard(long id);

    void deletOrderCard(long Id);

    OrderCard addProductToCard(long cardId, long productId);

    OrderCard removeProductFromCard(long cardId, long productId);

    List<Product> getOrders(long cardId);

    List<OrderCard> getAllOrderCards();

    Person addPerson(long id, Person person);

    List<OrderCard> getOrdersByPerson(String email);
}
