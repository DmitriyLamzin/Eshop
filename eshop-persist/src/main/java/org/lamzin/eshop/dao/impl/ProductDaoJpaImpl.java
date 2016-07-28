package org.lamzin.eshop.dao.impl;

import org.lamzin.eshop.model.catalog.Category;
import org.lamzin.eshop.model.catalog.Product;
import org.lamzin.eshop.model.catalog.SubCategory;
import org.lamzin.eshop.dao.interfaces.ProductDao;
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

    public ProductDaoJpaImpl() {
        super(Product.class);
    }

    public Product findById(String categoryId, String subcategoryId, long productId) {
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

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
        Root<Product> p = criteriaQuery.from(Product.class);
        Join<Product, SubCategory> sc = p.join("subCategory");
        criteriaQuery.select(p);

            criteriaQuery.where(
                    builder.equal(sc.get("subCategoryId").as(String.class), subCategory),
                    builder.ge(p.get("price").as(Double.class), minPrice),
                    builder.le(p.get("price").as(Double.class), maxPrice));

        if (!producer.isEmpty() && !producer.contains("all")){
            List<Predicate> predicates = new ArrayList<Predicate>();
            Iterator<String> iterator = producer.iterator();
            predicates.add(builder.equal(p.get("producer").as(String.class), iterator.next()));
            while (iterator.hasNext()){
                predicates.add(builder.or(builder.equal(p.get("producer").as(String.class), iterator.next())));
            }
            criteriaQuery.where(builder.or(predicates.toArray(new Predicate[predicates.size()])));
        }

        criteriaQuery.orderBy(builder.asc(p.get(orderBy)));

        Query query = em.createQuery(criteriaQuery);
        query.setFirstResult((page - 1) * amountPerPage);
        query.setMaxResults(amountPerPage);

        List<Product> products = query.getResultList();
        return products;
    }

    public void delete(String categoryId, String subcategoryId, long productId) {
        em.remove(findById(categoryId, subcategoryId, productId));
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

        criteriaQuery.where(
                builder.equal(sc.get("subCategoryId").as(String.class), subCategory),
                builder.ge(p.get("price").as(Double.class), minPrice),
                builder.le(p.get("price").as(Double.class), maxPrice));

        if (!producer.isEmpty() && !producer.contains("all")){
            List<Predicate> predicates = new ArrayList<Predicate>();
            Iterator<String> iterator = producer.iterator();
            predicates.add(builder.equal(p.get("producer").as(String.class), iterator.next()));
            while (iterator.hasNext()){
                predicates.add(builder.or(builder.equal(p.get("producer").as(String.class), iterator.next())));
            }
            criteriaQuery.where(builder.or(predicates.toArray(new Predicate[predicates.size()])));
        }

       return em.createQuery(criteriaQuery).getSingleResult();
    }
}
