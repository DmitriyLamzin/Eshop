package org.lamzin.eshop.dao.catalog.impl;

import org.lamzin.eshop.dao.catalog.interfaces.GenericDao;
import org.lamzin.eshop.entites.catalog.Product;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Dmitriy on 03.05.2016.
 */
@Repository("GenericDao")
@Scope (BeanDefinition.SCOPE_PROTOTYPE)
public class GenericDaoJpaImpl<T, Id> implements GenericDao<T, Id> {

    @PersistenceContext
    EntityManager em;

    private Class<T> type;

    public void setType (Class<T> typeToSet){
        this.type = typeToSet;
    }

    public GenericDaoJpaImpl(Class<T> type) {
        this.type = type;
    }

    public GenericDaoJpaImpl() {
    }

    public T save(T entity) {
        em.persist(entity);
        return entity;
    }

    public T update(T entity) {
        return em.merge(entity);

    }

    public T findById(Id entityId) {
        T entity = em.find(type, entityId);
        if (entity == null){
            throw new NoResultException();
        }
        else return entity;
    }

    public List<T> findAll() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(type);
        Root<T> p = criteriaQuery.from(type);
        criteriaQuery.select(p);

        Query query = em.createQuery(criteriaQuery);

        List<T> entityList = query.getResultList();
        return entityList;
    }

    public List<T> findAll(int page, int pageSize) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(type);
        Root<T> p = criteriaQuery.from(type);
        criteriaQuery.select(p);

        Query query = em.createQuery(criteriaQuery);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        List<T> entityList = query.getResultList();
        return entityList;
    }

    public Long count() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        criteriaQuery.select(builder.count(criteriaQuery.from(type)));

        return em.createQuery(criteriaQuery).getSingleResult();
    }

    public void delete(Id entityId) {
        T entity = findById(entityId);
        em.remove(entity);
    }

    public boolean exists(Id entityId) {
        try {
            return findById(entityId) != null;
        }catch (NoResultException e){
            return false;
        }
    }
}
