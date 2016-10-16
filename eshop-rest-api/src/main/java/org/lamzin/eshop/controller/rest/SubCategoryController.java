package org.lamzin.eshop.controller.rest;

import org.lamzin.eshop.dao.interfaces.SubCategoryDao;
import org.lamzin.eshop.dto.SubcategoryBasicDto;
import org.lamzin.eshop.dtoBuilder.SubCategoryDtoBuilder;
import org.lamzin.eshop.model.catalog.SubCategory;

import org.lamzin.eshop.service.interfaces.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Dmitriy on 01/02/2016.
 */
@RestController
@RequestMapping (value = "rest/{categoryId}/subcategories")
public class SubCategoryController {

    @Autowired
    private SubCategoryDtoBuilder subCategoryDtoWrapper;

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private SubCategoryDao subCategoryDao;

    @RequestMapping(method = RequestMethod.GET)
    public Resources<SubcategoryBasicDto> getAllSubCategories(@PathVariable String categoryId){
        Link link = linkTo(methodOn(this.getClass()).getAllSubCategories(categoryId)).withSelfRel();
        List<SubCategory> subcategories = subcategoryService.getSubcategories(categoryId);
        return new Resources<SubcategoryBasicDto>(subCategoryDtoWrapper.createListBasicDto(categoryId, subcategories),link);
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SubcategoryBasicDto> createSubCategory(@Valid @RequestBody SubCategory subCategory,
                                         @PathVariable String categoryId){
        ResponseEntity<SubcategoryBasicDto> responseEntity;
        try {
            SubCategory savedCategory = subcategoryService.save(subCategory, categoryId);
            responseEntity = new ResponseEntity<SubcategoryBasicDto>(
                    subCategoryDtoWrapper.createBasicDto(categoryId, savedCategory), HttpStatus.OK);
        }
        catch (NoResultException e){
            responseEntity = new ResponseEntity<SubcategoryBasicDto>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;

    }

    @RequestMapping(value = "/{subCategoryId}",
            method = RequestMethod.GET)
    public ResponseEntity<SubcategoryBasicDto> getSubCategoryById(@PathVariable String subCategoryId, @PathVariable String categoryId){

        try {
            SubcategoryBasicDto subcategoryBasicDto = subCategoryDtoWrapper.
                createBasicDto(categoryId, subCategoryDao.findById(categoryId, subCategoryId));
        return new ResponseEntity<SubcategoryBasicDto>(subcategoryBasicDto, HttpStatus.OK);
        }
        catch (NoResultException e){
            return new ResponseEntity<SubcategoryBasicDto>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/{subCategoryId}",
            method = RequestMethod.DELETE)
    public  ResponseEntity<SubcategoryBasicDto> deleteCategoryById(@PathVariable String subCategoryId, @PathVariable String categoryId){
        ResponseEntity<SubcategoryBasicDto> responseEntity;
        try {
            subcategoryService.deleteSubcategory(subCategoryId, categoryId);
            responseEntity = new ResponseEntity<SubcategoryBasicDto>(HttpStatus.OK);
        }
        catch (NoResultException e){
            responseEntity = new ResponseEntity<SubcategoryBasicDto>(HttpStatus.NOT_FOUND);
        }

        return responseEntity;

    }
}
