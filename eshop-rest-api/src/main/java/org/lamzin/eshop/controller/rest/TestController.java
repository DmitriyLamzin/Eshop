package org.lamzin.eshop.controller.rest;

import org.lamzin.eshop.model.CustomAuthorities;
import org.lamzin.eshop.model.CustomUser;
import org.lamzin.eshop.model.catalog.Product;

import org.lamzin.eshop.service.interfaces.CustomUserDetailsService;
import org.lamzin.eshop.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitriy on 26.04.2016.
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private ProductService productService;

    @Autowired
    @Qualifier("customUserDetailsService")
    private CustomUserDetailsService customUserDetailsService;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setCustomUserDetailsService(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @RequestMapping("/{categoryId}/{subcategoryId}")
    public void setProducts(@PathVariable String categoryId, @PathVariable String subcategoryId){
        for (int i = 0; i<30; i++){
            Product product = new Product();
            product.setProducer("producer_" + Integer.toString(1000 - i));
            product.setPrice(1000 - i * 10);
            product.setId(i);
            product.setName("name_" + i);
            productService.addProduct(product, subcategoryId, categoryId);
        }
    }

    @RequestMapping("/{username}")
    public void setSuperUser(@PathVariable String username){
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new CustomAuthorities("ROLE_ADMIN"));
        authorities.add(new CustomAuthorities("ROLE_USER"));
        CustomUser user = new CustomUser(username, "pass", authorities);
        customUserDetailsService.saveUser(user);
    }

}
