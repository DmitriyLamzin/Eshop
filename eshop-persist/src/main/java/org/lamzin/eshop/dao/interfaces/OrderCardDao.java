package org.lamzin.eshop.dao.interfaces;

import org.lamzin.eshop.entites.OrderCard;

import java.util.List;

/**
 * Created by Dmitriy on 24.06.2016.
 */
public interface OrderCardDao extends GenericDao<OrderCard, Long> {
    List<OrderCard> findAllOrders(String email);
}
