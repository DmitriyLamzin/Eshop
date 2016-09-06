package org.lamzin.eshop.viewcontroller;

import org.lamzin.eshop.dto.CategoryBasicDto;
import org.lamzin.eshop.dto.ProductBasicDto;
import org.lamzin.eshop.dto.SubcategoryBasicDto;
import org.lamzin.eshop.dtoBuilder.CategoryDtoBuilder;
import org.lamzin.eshop.dtoBuilder.ProductDtoBuilder;
import org.lamzin.eshop.dtoBuilder.SubCategoryDtoBuilder;
import org.lamzin.eshop.model.catalog.Category;
import org.lamzin.eshop.model.catalog.Product;
import org.lamzin.eshop.model.catalog.SubCategory;
import org.lamzin.eshop.service.interfaces.CategoryService;
import org.lamzin.eshop.service.interfaces.ProductService;
import org.lamzin.eshop.service.interfaces.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitriy on 18/02/2016.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryDtoBuilder categoryDtoBuilder;
    @Autowired
    SubcategoryService subcategoryService;
    @Autowired
    SubCategoryDtoBuilder subCategoryDtoBuilder;
    @Autowired
    ProductService productService;
    @Autowired
    ProductDtoBuilder productDtoBuilder;

    @RequestMapping(
            method = RequestMethod.GET)
    public String getAdminPage(){
        return "redirect:/admin/catalog";
    }

    @RequestMapping(value = "/catalog", method = RequestMethod.GET)
    public ModelAndView getCatalog(){
        ModelAndView model = new ModelAndView("catalog");
        try{
            List<Category> categoryList = categoryService.getAllCategories();
            List<CategoryBasicDto> categories = categoryDtoBuilder.createBasicDtoList(categoryList);
            model.addObject("categories", categories);
        }catch (Exception e){

        }
        return model;
    }

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String getCategoryAdminPage(){
        return "category";
    }

    @RequestMapping(value = "/subcategory", method = RequestMethod.GET)
    public String getSubCategoryAdminPage(){
        return "subcategory";
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ModelAndView getProductAdminPage()
    {
        ModelAndView modelandview = new ModelAndView("product");

        try {
            List<Category> categoryList = categoryService.getAllCategories();
            Category firstCategory = categoryList.get(0);
            List<SubCategory> subCategoryList = subcategoryService.getSubcategories(firstCategory.getCategoryId());
            List<Product> productList = productService.getMultipleProducts(subCategoryList.get(0).getSubCategoryId(),
                    categoryList.get(0).getCategoryId(),
                    0.0,
                    1000000.0,
                    new ArrayList<String>(),
                    "price",
                    1,
                    20);

            List<CategoryBasicDto> categories = categoryDtoBuilder.createBasicDtoList(categoryList);
            List<SubcategoryBasicDto> subCategories = subCategoryDtoBuilder.createListBasicDto(firstCategory.getCategoryId(),
                    subCategoryList);
            List<ProductBasicDto> products =  productDtoBuilder.createListBasicDto(productList);
            modelandview.addObject("categories", categories)
                    .addObject("subCategories", subCategories)
                    .addObject("products", products);
        }catch (Exception e){

        }

        return modelandview;
    }

}
