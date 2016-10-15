package org.lamzin.eshop.controller.rest;

import org.lamzin.eshop.dao.interfaces.ProductDao;
import org.lamzin.eshop.dto.MultipleProductsRequestObject;
import org.lamzin.eshop.dto.ProductBasicDto;
import org.lamzin.eshop.dtoBuilder.ProductDtoBuilder;
import org.lamzin.eshop.model.catalog.Product;

import org.lamzin.eshop.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping(value = "rest/{categoryId}/subcategories/{subCategoryId}/products")
public class ProductController {

    @Autowired
    ProductDtoBuilder productDtoBuilder;

    @Autowired
    ProductService productService;

    @Autowired
    ProductDao productDao;


    @RequestMapping(value = "rest/{categoryId}/subcategories/{subCategoryId}/products",
            method = RequestMethod.GET)
    public Resources<ProductBasicDto> getMultipleProducts (@PathVariable String subCategoryId, @PathVariable String categoryId,
                                               @ModelAttribute("multipleProductRequestObject") MultipleProductsRequestObject multipleProductsRequestObject){
        double minPrice = multipleProductsRequestObject.getMinPrice();
        double maxPrice = multipleProductsRequestObject.getMaxPrice();
        List<String> producer = multipleProductsRequestObject.getProducer();
        String orderBy = multipleProductsRequestObject.getOrderBy();
        int page = multipleProductsRequestObject.getPage();
        int pageSize = multipleProductsRequestObject.getPageSize();

        List<Product> list = productService.getMultipleProducts(subCategoryId, categoryId, minPrice, maxPrice, producer, orderBy, page, pageSize);

        List<ProductBasicDto> productBasicDtoList = productDtoBuilder.createListBasicDto(list);

        List<Link> links = new ArrayList<Link>();

        links.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ProductController.class).
                getMultipleProducts(subCategoryId, categoryId, multipleProductsRequestObject)).
                withSelfRel());

        links.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ProductController.class).
                getSize(subCategoryId, categoryId, minPrice, maxPrice, producer)).
                withRel("total_size"));

        MultipleProductsRequestObject nextPage = new MultipleProductsRequestObject(multipleProductsRequestObject);
        nextPage.setPage(nextPage.getPage() + 1);

        links.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ProductController.class).
                getMultipleProducts(subCategoryId, categoryId, nextPage)).
                withRel("next"));

        MultipleProductsRequestObject prevPage = new MultipleProductsRequestObject(multipleProductsRequestObject);
        nextPage.setPage(prevPage.getPage() - 1);

        if (page > 1) {
            links.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ProductController.class).
                    getMultipleProducts(subCategoryId, categoryId, prevPage)).
                    withRel("prev"));
        }

        return new Resources<ProductBasicDto>(productBasicDtoList, links);
    }

    @RequestMapping(value = "rest/{categoryId}/subcategories/{subCategoryId}/products/size",
            method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Long> getSize (@PathVariable String subCategoryId, @PathVariable String categoryId,
                                                           @RequestParam(value = "minPrice", required = false, defaultValue = "0") Double minPrice,
                                                           @RequestParam(value = "maxPrice", required = false, defaultValue = "100000") Double maxPrice,
                                                           @RequestParam(value = "producer", required = false, defaultValue = "all") List<String> producer){

        return new ResponseEntity<Long>(productService.getSize(subCategoryId, categoryId, minPrice, maxPrice, producer),
                HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,
            value = "rest/{categoryId}/subcategories/{subCategoryId}/products")
    public ResponseEntity<ProductBasicDto> createProduct(@RequestBody Product product,
                                 @PathVariable String subCategoryId,
                                 @PathVariable String categoryId){
        Product savedProduct = productService.addProduct(product, subCategoryId, categoryId);
        return  new ResponseEntity<ProductBasicDto>(
                productDtoBuilder.createBasicDto(savedProduct, subCategoryId,categoryId), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.PUT,
            value = "rest/{categoryId}/subcategories/{subCategoryId}/products")
    public ResponseEntity<ProductBasicDto> updateProduct(@RequestBody Product product,
                                                         @PathVariable String subCategoryId,
                                                         @PathVariable String categoryId){
        Product updateProduct = productService.updateProduct(product, subCategoryId, categoryId);
        return  new ResponseEntity<ProductBasicDto>(
                productDtoBuilder.createBasicDto(updateProduct, subCategoryId, categoryId), HttpStatus.OK);

    }

    @RequestMapping(value = "rest/{categoryId}/subcategories/{subCategoryId}/products/{productId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Product> getProductById(@PathVariable long productId, @PathVariable String subCategoryId, @PathVariable String categoryId){
        return new ResponseEntity<Product>(productService.getProductById(productId, subCategoryId, categoryId), HttpStatus.OK);
    }

    @RequestMapping(value = "rest/allProducts/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Product> getProductById(@PathVariable long id){
        return new ResponseEntity<Product>(productService.getProductById(id), HttpStatus.OK);
    }


    @RequestMapping(value = "rest/allProducts/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteProductById(@PathVariable long id){
        ResponseEntity responseEntity;
        try {
            productService.deleteProduct(id);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        }catch (NoResultException e){
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @RequestMapping(value = "rest/{categoryId}/subcategories/{subCategoryId}/products/{productId}",
            method = RequestMethod.DELETE)
    public ResponseEntity deleteCategoryById(@PathVariable long productId, @PathVariable String subCategoryId, @PathVariable String categoryId){
        ResponseEntity responseEntity;
        try {
            productService.deleteProduct(productId, subCategoryId, categoryId);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        }catch (NoResultException e){
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }
}
