package org.lamzin.eshop.service;

import org.lamzin.eshop.dao.interfaces.GenericDao;
import org.lamzin.eshop.dao.interfaces.OrderCardDao;
import org.lamzin.eshop.dao.interfaces.ProductDao;
import org.lamzin.eshop.model.OrderCard;
import org.lamzin.eshop.model.Person;
import org.lamzin.eshop.model.catalog.Product;
import org.lamzin.eshop.service.interfaces.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Dmitriy on 13.06.2016.
 */
@Service("cardService")
@Transactional
public class CardServiceImpl implements CardService {

    private GenericDao<Person, String> personDao;

    @Autowired
    private OrderCardDao dao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    @Qualifier("GenericDao")
    public void setPersonDao(GenericDao<Person, String> genericDao){
        this.personDao = genericDao;
        this.personDao.setType(Person.class);
    }

    public OrderCard createOrderCard(OrderCard orderCard){
        if(!personDao.exists(orderCard.getPersonId())){
            orderCard.setPerson(personDao.save(orderCard.getPerson()));
        }else {
            orderCard.setPerson(personDao.findById(orderCard.getPersonId()));
        }
        return dao.save(orderCard);
    }

    public OrderCard getOrderCard(long id){
        return dao.findById(id);
    }

    public void deletOrderCard(long Id){
        dao.delete(Id);
    }

    public OrderCard addProductToCard(long cardId, long productId){
        OrderCard card = dao.findById(cardId);
        card.addProduct(productDao.findById(productId));

        return card;
    }

    public OrderCard removeProductFromCard(long cardId, long productId){
        OrderCard card = dao.findById(cardId);
        card.removeProduct(productDao.findById(productId));
        return card;
    }

    public List<Product> getOrders(long cardId){
        return dao.findById(cardId).getProducts();
    }


    public List<OrderCard> getAllOrderCards() {
        return dao.findAll();
    }

    public Person addPerson(long id, Person person) {
        if (personDao.exists(person.getEmail()))
            getOrderCard(id).setPerson(personDao.findById(person.getEmail()));
        else {
            personDao.save(person);
            getOrderCard(id).setPerson(person);
        }
        return personDao.findById(person.getEmail());
    }

    public List<OrderCard> getOrdersByPerson(String email){
        return dao.findAllOrders(email);
    }
}
