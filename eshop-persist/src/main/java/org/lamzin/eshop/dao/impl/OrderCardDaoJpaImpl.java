package org.lamzin.eshop.dao.impl;

import org.lamzin.eshop.dao.interfaces.OrderCardDao;

import org.lamzin.eshop.model.OrderCard;
import org.lamzin.eshop.model.Person;
import org.lamzin.eshop.model.catalog.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Dmitriy on 24.06.2016.
 */
@Repository("orderCardDao")
public class OrderCardDaoJpaImpl extends GenericDaoJpaImpl<OrderCard, Long> implements OrderCardDao {

    public OrderCardDaoJpaImpl() {
        super(OrderCard.class);
    }

    public List<OrderCard> findAllOrders(String email) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<OrderCard> criteriaQuery = builder.createQuery(OrderCard.class);
        Root<OrderCard> card = criteriaQuery.from(OrderCard.class);
        Join<OrderCard, Person> person = card.join("person");
        
        criteriaQuery.select(card);

        criteriaQuery.where(
                builder.equal(person.get("email").as(String.class), email)
        );
        return em.createQuery(criteriaQuery).getResultList();
    }
}
