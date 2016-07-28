package org.lamzin.eshop.dao.interfaces;

import java.util.List;

/**
 * Created by Dmitriy on 29.04.2016.
 */
public interface GenericDao<T, Id> {

    Class<?> type = null;

    void setType(Class<T> typeToSet);

    T save(T entity);

    T update(T entity);

    T findById(Id entityId);

    List<T> findAll();

    List<T> findAll(int page, int pageSize);

    Long count();

    void delete(Id entityId);

    boolean exists(Id entityId);
}
