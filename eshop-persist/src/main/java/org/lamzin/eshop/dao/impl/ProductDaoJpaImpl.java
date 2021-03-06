package org.lamzin.eshop.dao.impl;

import org.apache.log4j.Logger;
import org.lamzin.eshop.model.catalog.Category;
import org.lamzin.eshop.model.catalog.Product;
import org.lamzin.eshop.model.catalog.SubCategory;
import org.lamzin.eshop.dao.interfaces.ProductDao;
import org.lamzin.eshop.dao.exception.NotAllowedToDeleteException;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Dmitriy on 04/11/2015.
 */

@Repository ("productDao")
public class ProductDaoJpaImpl extends GenericDaoJpaImpl<Product, Long> implements ProductDao {

    private final Logger logger = Logger.getLogger(this.getClass());
    public ProductDaoJpaImpl() {
        super(Product.class);
    }

    public Product findById(String categoryId, String subcategoryId, long productId) {
        logger.info("method findById with arguments " + categoryId + " " + subcategoryId + " " + productId +
        " is called");
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
        Root<Product> p = criteriaQuery.from(Product.class);
        Join<Product, SubCategory> sc = p.join("subCategory");

        Root<Category> categoryRoot = criteriaQuery.from(Category.class);
        Join<SubCategory, Category> categoryJoin = categoryRoot.join("subCategories");

        criteriaQuery.select(p);

        criteriaQuery.where(
                builder.equal(sc.get("subCategoryId").as(String.class), subcategoryId),
                builder.and(builder.equal(categoryRoot.get("categoryId").as(String.class), categoryId),
                        builder.equal(categoryJoin.get("subCategoryId"), subcategoryId)),
                builder.equal(p.get("id").as(Long.class), productId));

        return em.createQuery(criteriaQuery).getSingleResult();
    }

    public List<Product> getMultipleProducts(String subCategory,
                                             double minPrice,
                                             double maxPrice,
                                             List<String> producer,
                                             String orderBy,
                                             int page,
                                             int amountPerPage) {

        logger.info("method getMultipleProducts with arguments " + subCategory +
                " " + minPrice + " " + maxPrice + " " + producer + " " + orderBy +
                " " + page + " " + amountPerPage + " is called");
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
        Root<Product> p = criteriaQuery.from(Product.class);
        Join<Product, SubCategory> sc = p.join("subCategory");
        criteriaQuery.select(p);

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(builder.equal(sc.get("subCategoryId").as(String.class), subCategory));
        predicates.add(builder.ge(p.get("price").as(Double.class), minPrice));
        predicates.add(builder.le(p.get("price").as(Double.class), maxPrice));

        if (!producer.isEmpty() && !producer.contains("all")){
            List<Predicate> producerPredicates = new ArrayList<Predicate>();
            Iterator<String> iterator = producer.iterator();
            producerPredicates.add(builder.equal(p.get("producer").as(String.class), iterator.next()));
            while (iterator.hasNext()){
                producerPredicates.add(builder.or(builder.equal(p.get("producer").as(String.class), iterator.next())));
            }
            criteriaQuery.where(
                    builder.or(producerPredicates.toArray(new Predicate[producerPredicates.size()])),
                    builder.and(predicates.toArray(new Predicate[predicates.size()])));
        }else {
            criteriaQuery.where(
                    builder.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        criteriaQuery.orderBy(builder.asc(p.get(orderBy)));

        Query query = em.createQuery(criteriaQuery);
        query.setFirstResult((page - 1) * amountPerPage);
        query.setMaxResults(amountPerPage);

        List<Product> products = query.getResultList();
        return products;
    }

    public void delete(String categoryId, String subcategoryId, long productId) {

        Product product = findById(categoryId, subcategoryId, productId);
        if (!product.hasObservers())
            em.remove(product);
        else {
            throw new NotAllowedToDeleteException("The Product is now ordered by " + product.getObserverItems());
        }

    }

    @Override
    public void delete(Long entityId) {
        Product product = findById(entityId);
        if (!product.hasObservers())
            em.remove(product);
        else {
            throw new NotAllowedToDeleteException("The Product is now ordered by " + product.getObserverItems());
        }

    }

    public Long count(String subCategory,
                      double minPrice,
                      double maxPrice,
                      List<String> producer) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Product> p = criteriaQuery.from(Product.class);
        Join<Product, SubCategory> sc = p.join("subCategory");

        criteriaQuery.select(builder.count(p));

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(builder.equal(sc.get("subCategoryId").as(String.class), subCategory));
        predicates.add(builder.ge(p.get("price").as(Double.class), minPrice));
        predicates.add(builder.le(p.get("price").as(Double.class), maxPrice));

        if (!producer.isEmpty() && !producer.contains("all")){
            List<Predicate> producerPredicates = new ArrayList<Predicate>();
            Iterator<String> iterator = producer.iterator();
            producerPredicates.add(builder.equal(p.get("producer").as(String.class), iterator.next()));
            while (iterator.hasNext()){
                producerPredicates.add(builder.or(builder.equal(p.get("producer").as(String.class), iterator.next())));
            }
            criteriaQuery.where(
                    builder.or(producerPredicates.toArray(new Predicate[producerPredicates.size()])),
                    builder.and(predicates.toArray(new Predicate[predicates.size()])));
        }else {
            criteriaQuery.where(
                    builder.and(predicates.toArray(new Predicate[predicates.size()])));
        }

       return em.createQuery(criteriaQuery).getSingleResult();
    }
}
