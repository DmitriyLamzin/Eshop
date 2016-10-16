package org.lamzin.eshop.controller.rest;

import org.lamzin.eshop.dao.interfaces.CategoryDao;
import org.lamzin.eshop.dto.CategoryBasicDto;
import org.lamzin.eshop.dtoBuilder.CategoryDtoBuilder;
import org.lamzin.eshop.model.catalog.Category;

import org.lamzin.eshop.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Dmitriy on 31/01/2016.
 */
@RestController
@RequestMapping("/rest")
public class CategoryController {

    @Autowired
    private CategoryDtoBuilder categoryDtoWrapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryDao categoryDao;


    @RequestMapping(
            method = RequestMethod.GET)
    public ResponseEntity<Resources<CategoryBasicDto>> getAllCategories(){
        Link link = linkTo(methodOn(CategoryController.class).getAllCategories()).withSelfRel();
        try {
            List<Category> categoryList = categoryService.getAllCategories();
            List<CategoryBasicDto> categoryBasicDtoList = categoryDtoWrapper.createBasicDtoList(categoryList);
            Resources<CategoryBasicDto> categoryBasicDtoResources = new Resources<CategoryBasicDto>(categoryBasicDtoList,link);

            return new ResponseEntity<Resources<CategoryBasicDto>>(categoryBasicDtoResources, HttpStatus.OK);
        }catch (NoResultException e){
            return new ResponseEntity<Resources<CategoryBasicDto>>(HttpStatus.NOT_FOUND);
        }

    }
    @RequestMapping(
            method = RequestMethod.POST)
    public ResponseEntity<CategoryBasicDto> createCategory(@Valid @RequestBody Category category){

        return new ResponseEntity<CategoryBasicDto>(categoryDtoWrapper.createBasicDto(categoryService.addCategory(category)), HttpStatus.OK);

    }

    @RequestMapping(value = "/{categoryId}",
            method = RequestMethod.GET)
    public ResponseEntity<CategoryBasicDto> getCategoryById(@PathVariable String categoryId){
        ResponseEntity<CategoryBasicDto> responseEntity;
        try {
            Category category = categoryService.getCategoryByName(categoryId);
            CategoryBasicDto categoryBasicDto = categoryDtoWrapper.createBasicDto(category);
            responseEntity = new ResponseEntity<CategoryBasicDto>(categoryBasicDto, HttpStatus.OK);
        }
        catch (NoResultException e){
            responseEntity = new ResponseEntity<CategoryBasicDto>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    @RequestMapping(value = "/{categoryId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteCategoryById(@PathVariable String categoryId){

        ResponseEntity responseEntity;
        try {
            categoryService.deleteCategory(categoryId);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        }
        catch (NoResultException e){
            responseEntity = new ResponseEntity<CategoryBasicDto>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }
}
