package org.lamzin.eshop.service.interfaces;

import org.lamzin.eshop.model.OrderCard;
import org.lamzin.eshop.model.Person;
import org.lamzin.eshop.model.catalog.Product;

import java.util.List;

/**
 * Created by Dmitriy on 15.07.2016.
 */
public interface CardService {

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
