package org.lamzin.eshop.dao.impl;

import org.apache.log4j.Logger;
import org.lamzin.eshop.dao.interfaces.GenericDao;
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
import java.util.List;

@Repository("GenericDao")
@Scope (BeanDefinition.SCOPE_PROTOTYPE)
public class GenericDaoJpaImpl<T, Id> implements GenericDao<T, Id> {

    @PersistenceContext
    EntityManager em;

    private Class<T> type;

    private final Logger logger = Logger.getLogger(this.getClass());

    public void setType (Class<T> typeToSet){
        logger.info("the type set to " + typeToSet);
        this.type = typeToSet;
    }

    public GenericDaoJpaImpl(Class<T> type) {
        logger.info("the type set to " + type);
        this.type = type;
    }

    public GenericDaoJpaImpl() {
    }

    public T save(T entity) {
        logger.info("save entity " + entity);
        em.persist(entity);
        return entity;
    }

    public T update(T entity) {
        logger.info("update entity " + entity);
        return em.merge(entity);

    }

    public T findById(Id entityId) {
        logger.info("find entity by Id " + entityId);
        T entity = em.find(type, entityId);
        if (entity == null){
            if (logger.isDebugEnabled()){
                logger.debug("entity with Id = " + entityId + " is not exist, the NoResultException will be thrown");
            }
            throw new NoResultException();
        }
        else{
            logger.info("The entity with Id " + entityId + " is found = " + entity.toString());
            return entity;
        }
    }

    public List<T> findAll() {
        logger.info("find all method is called");
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(type);
        Root<T> p = criteriaQuery.from(type);
        criteriaQuery.select(p);

        Query query = em.createQuery(criteriaQuery);

        List<T> entityList =  query.getResultList();
        logger.info("found the list of all items of type " + type + " is a " + entityList.toString());
        return entityList;
    }

    public List<T> findAll(int page, int pageSize) {
        logger.info("find all with page number " + page + "and page size " + pageSize + " is called");
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(type);
        Root<T> p = criteriaQuery.from(type);
        criteriaQuery.select(p);

        Query query = em.createQuery(criteriaQuery);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        List<T> entityList = query.getResultList();
        logger.info("found the list of all items of type " + type + " is a " + entityList.toString());
        return entityList;
    }

    public Long count() {
        logger.info("the 'count' method is called");
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        criteriaQuery.select(builder.count(criteriaQuery.from(type)));

        Long count = em.createQuery(criteriaQuery).getSingleResult();
        logger.info("the count of items with type " + type + " equals " + count);
        return count;
    }

    public void delete(Id entityId) {
        logger.info("the 'delete' method for entity with Id " + entityId + " is called");
        T entity = findById(entityId);
        em.remove(entity);
    }

    public boolean exists(Id entityId) {
        logger.info("the 'exist' method for entity with Id " + entityId + " is called");
        try {
            return findById(entityId) != null;
        }catch (NoResultException e){
            if (logger.isDebugEnabled()){
                logger.debug("entity with Id = " + entityId + " is not exist, the 'false' will be returned");
            }
            return false;
        }
    }
}
