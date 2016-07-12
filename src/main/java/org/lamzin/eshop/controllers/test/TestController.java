package org.lamzin.eshop.controllers.test;

import org.lamzin.eshop.entites.catalog.Product;
import org.lamzin.eshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dmitriy on 26.04.2016.
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    ProductService productService;

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
}
