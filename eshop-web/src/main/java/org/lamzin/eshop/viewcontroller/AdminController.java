package org.lamzin.eshop.viewcontroller;

import org.lamzin.eshop.dto.CategoryBasicDto;
import org.lamzin.eshop.dto.OrderCardExtendedDto;
import org.lamzin.eshop.dto.ProductBasicDto;
import org.lamzin.eshop.dto.SubcategoryBasicDto;
import org.lamzin.eshop.dtoBuilder.CategoryDtoBuilder;
import org.lamzin.eshop.dtoBuilder.OrderCardDtoBuilder;
import org.lamzin.eshop.dtoBuilder.ProductDtoBuilder;
import org.lamzin.eshop.dtoBuilder.SubCategoryDtoBuilder;
import org.lamzin.eshop.model.OrderCard;
import org.lamzin.eshop.model.catalog.Category;
import org.lamzin.eshop.model.catalog.Product;
import org.lamzin.eshop.model.catalog.SubCategory;
import org.lamzin.eshop.service.interfaces.CardService;
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
    private CategoryService categoryService;
    @Autowired
    private CategoryDtoBuilder categoryDtoBuilder;
    @Autowired
    private SubcategoryService subcategoryService;
    @Autowired
    private SubCategoryDtoBuilder subCategoryDtoBuilder;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDtoBuilder productDtoBuilder;
    @Autowired
    private CardService cardService;
    @Autowired
    private OrderCardDtoBuilder orderCardDtoBuilder;

    private final double minPrice = 0.0;
    private final double maxPrice = 10000000.0;
    private final ArrayList<String> producersList = new ArrayList<String>();
    private final String sortBy = "price";
    private final int firstPage = 1;
    private final int pageSize = 20;


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

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public ModelAndView getOrderManager(){
        ModelAndView model = new ModelAndView("order");
        try{
            List<OrderCard> orderCards = cardService.getAllOrderCards();
            List<OrderCardExtendedDto> orderCardsDtoList = orderCardDtoBuilder.buildListExtended(orderCards);
            model.addObject("orders", orderCardsDtoList);
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
                    minPrice,
                    maxPrice,
                    producersList,
                    sortBy,
                    firstPage,
                    pageSize);

            long querySize = productService.getSize(subCategoryList.get(0).getSubCategoryId(),
                    categoryList.get(0).getCategoryId(),
                    minPrice,
                    maxPrice,
                    producersList);

            long pageNumbers = 0;

            if (querySize % pageSize != 0){
                pageNumbers = querySize/pageSize + 1;
            }
            else
                pageNumbers = querySize/pageSize;

            List<CategoryBasicDto> categories = categoryDtoBuilder.createBasicDtoList(categoryList);
            List<SubcategoryBasicDto> subCategories = subCategoryDtoBuilder.createListBasicDto(firstCategory.getCategoryId(),
                    subCategoryList);
            List<ProductBasicDto> products =  productDtoBuilder.createListBasicDto(productList);
            modelandview.addObject("categories", categories)
                    .addObject("subCategories", subCategories)
                    .addObject("products", products)
                    .addObject("pageNumbers", pageNumbers);



        }catch (Exception e){

        }

        return modelandview;
    }

}
