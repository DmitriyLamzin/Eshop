package org.lamzin.eshop.service;

import org.apache.log4j.Logger;
import org.lamzin.eshop.model.CustomAuthorities;
import org.lamzin.eshop.model.CustomUser;
import org.lamzin.eshop.model.catalog.Category;
import org.lamzin.eshop.model.catalog.Product;
import org.lamzin.eshop.model.catalog.SubCategory;
import org.lamzin.eshop.service.interfaces.CategoryService;
import org.lamzin.eshop.service.interfaces.CustomUserDetailsService;
import org.lamzin.eshop.service.interfaces.ProductService;
import org.lamzin.eshop.service.interfaces.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitriy on 05.09.2016.
 */
@Component
public class InitUser implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubcategoryService subcategoryService;

    private final Logger log = Logger.getLogger(this.getClass());

    private int contextRefreshedCount = 0;
    private final String categoryName="categoryName";
    private final String categoryId="categoryId";
    private final String subCategoryName="subCategoryName";
    private final String subCategoryId="subCategoryId";

    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("context refreshed");

        if (contextRefreshedCount == 0) {
            Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            authorities.add(new CustomAuthorities("ROLE_ADMIN"));
            authorities.add(new CustomAuthorities("ROLE_USER"));
            CustomUser user = new CustomUser("SuperUser", "SuperPass", authorities);
            customUserDetailsService.saveUser(user);

            Category category = new Category();
            category.setName(categoryName);
            category.setCategoryId(categoryId);
            categoryService.addCategory(category);

            SubCategory subCategory = new SubCategory();
            subCategory.setSubCategoryId(subCategoryId);
            subCategory.setSubCategoryName(subCategoryName);
            subcategoryService.save(subCategory, categoryId);


            for (int i = 0; i < 30; i++) {
                Product product = new Product();
                product.setProducer("producer_" + Integer.toString(1000 - i));
                product.setPrice(1000 - i * 10);
                product.setId(i);
                product.setName("name_" + i);
                productService.addProduct(product, subCategoryId, categoryId);
            }

            contextRefreshedCount++;
        }
    }

}
