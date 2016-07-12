package org.lamzin.eshop.dao.catalog.impl;

import org.lamzin.eshop.dao.catalog.interfaces.SubCategoryDao;
import org.lamzin.eshop.entites.catalog.Category;
import org.lamzin.eshop.entites.catalog.Product;
import org.lamzin.eshop.entites.catalog.SubCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Dmitriy on 05.05.2016.
 */
@Repository("subCategoryDao")
public class SubcategoryDaoJpaImpl extends GenericDaoJpaImpl<SubCategory, String> implements SubCategoryDao {

    public SubcategoryDaoJpaImpl() {
        super(SubCategory.class);
    }

    public SubCategory findById(String categoryId, String subcategoryId) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<SubCategory> criteriaQuery = builder.createQuery(SubCategory.class);
        Root<Category> c = criteriaQuery.from(Category.class);
        Join<Category, SubCategory> sc = c.join("subCategories");

        criteriaQuery.select(sc);

        criteriaQuery.where(
                builder.and(builder.equal(c.get("categoryId").as(String.class), categoryId),
                        builder.equal(sc.get("subCategoryId"), subcategoryId)),
                        builder.equal(c.get("categoryId").as(String.class), categoryId)
                );

        return em.createQuery(criteriaQuery).getSingleResult();
    }

    public List<SubCategory> findByCategory (String categoryId){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<SubCategory> criteriaQuery = builder.createQuery(SubCategory.class);
        Root<Category> c = criteriaQuery.from(Category.class);
        Join<Category, SubCategory> sc = c.join("subCategories");

        criteriaQuery.select(sc);

        criteriaQuery.where(
                builder.equal(c.get("categoryId").as(String.class), categoryId)
        );

        Query query = em.createQuery(criteriaQuery);

        List<SubCategory> entityList = query.getResultList();
        return entityList;
    }

    public void delete(String categoryId, String subcategoryId) {
        em.remove(findById(categoryId, subcategoryId));
    }
}